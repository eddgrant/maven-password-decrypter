package com.eddgrant.mpd

import org.junit.Test

import static org.junit.Assert.fail

class SettingsSecurityParserTest {

  @Test
  public void shouldParseSettingsSecurityFile() {
    final String expectedValue = "{t0hO7kTVVJgHnxGDD1Fovy3pGnht80FICNIZ4FE3Adw=}"
    def settingsSecurityFile = "src/test/resources/test-files/settings-security.xml"
    final String actualValue = new SettingsSecurityParser().parse(settingsSecurityFile)
    assert expectedValue == actualValue
  }

  @Test
  public void shouldReturnEmptyStringIfFileDoesNotContainMasterElement() {
    def settingsSecurityFile = "src/test/resources/test-files/settings-security-no-master-element.xml"
    assert "" == new SettingsSecurityParser().parse(settingsSecurityFile)
  }

  @Test
  public void shouldThrowExceptionIfFileDoesNotExist() {
    final String settingsSecurityFile = "this file does not exist"
    final String expectedErrorMessage = "Error: Specified security Settings file: ${settingsSecurityFile} was not found"
    try {
      new SettingsSecurityParser().parse(settingsSecurityFile)
      fail("Exception should have been thrown but was not.")
    }
    catch (FileNotFoundException fnfe) {
      assert expectedErrorMessage == fnfe.message
    }
  }
}
