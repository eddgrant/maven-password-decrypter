package com.eddgrant.mpd

import org.junit.Before
import org.junit.Test

import static org.junit.Assert.fail

class MavenPasswordDecrypterTest {

  private static final String MASTER_PASSWORD = "This is my master password"

  private static final String SETTINGS_SECURITY_FILE_PATH = "src/test/resources/test-files/settings-security.xml"

  private static final String SETTINGS_XML_FILE_PATH = "src/test/resources/test-files/settings-with-multiple-server-entries.xml"

  private SettingsSecurityParser settingsSecurityParser

  private SettingsXmlParser settingsXmlParser

  private MavenPasswordDecrypter mavenPasswordDecrypter

  @Before
  public void setUp() {
    settingsSecurityParser = new SettingsSecurityParser()
    settingsXmlParser = new SettingsXmlParser()
    mavenPasswordDecrypter = new MavenPasswordDecrypter(settingsSecurityParser, settingsXmlParser)
  }

  @Test
  public void shouldDecryptMasterPassword() {
    assert MASTER_PASSWORD == mavenPasswordDecrypter.getMasterPassword(SETTINGS_SECURITY_FILE_PATH)
  }

  @Test
  public void shouldDecryptServerPasswords() {
    final List<ServerDetails> returnedServerDetails = mavenPasswordDecrypter.getServerDetails(SETTINGS_XML_FILE_PATH,
                                                                                              MASTER_PASSWORD)
    (1..3).each { index ->
      assert "${index}" == returnedServerDetails[index -1].id
      assert "u${index}" == returnedServerDetails[index -1].username
      assert "This is server ${index}'s password" == returnedServerDetails[index - 1].decryptedPassword
    }
  }

  @Test
  public void shouldReturnServerDetail() {
    final ServerDetails serverDetails = mavenPasswordDecrypter.getServerDetail(SETTINGS_SECURITY_FILE_PATH,
                                                                               SETTINGS_XML_FILE_PATH,
                                                                               "1")
    assert "1" == serverDetails.id
    assert "u1" == serverDetails.username
    assert "This is server 1's password" == serverDetails.decryptedPassword

  }

  @Test
  public void shouldThrowExceptionWhenInvalidServerSpecified() {
    def serverId = "This server doesn't exist in the file"
    try {
      mavenPasswordDecrypter.getServerDetail(SETTINGS_SECURITY_FILE_PATH,
                                             SETTINGS_XML_FILE_PATH,
                                             serverId)
      fail("Exception should have been thrown but was not")
    }
    catch (Exception e) {
      assert e instanceof IllegalArgumentException
      assert "Server with id: '${serverId}' was not found in file: '${SETTINGS_XML_FILE_PATH}'"
    }
  }
}
