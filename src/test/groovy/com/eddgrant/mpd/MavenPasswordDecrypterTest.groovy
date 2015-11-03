package com.eddgrant.mpd

import org.junit.Before
import org.junit.Test

class MavenPasswordDecrypterTest {

  private static final String MASTER_PASSWORD = "This is my master password"

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
    final String filePath = "src/test/resources/test-files/settings-security.xml"
    assert MASTER_PASSWORD == mavenPasswordDecrypter.getMasterPassword(filePath)
  }

  @Test
  public void shouldDecryptServerPasswords() {
    final String filePath = "src/test/resources/test-files/settings-with-multiple-server-entries.xml"
    final List<ServerDetails> returnedServerDetails = mavenPasswordDecrypter.getServerDetails(filePath, MASTER_PASSWORD)
    (1..3).each { index ->
      assert "${index}" == returnedServerDetails[index -1].id
      assert "u${index}" == returnedServerDetails[index -1].username
      assert "This is server ${index}'s password" == returnedServerDetails[index - 1].decryptedPassword
    }
  }
}
