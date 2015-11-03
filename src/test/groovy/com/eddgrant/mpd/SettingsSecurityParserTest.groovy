package com.eddgrant.mpd

import org.junit.Test

import static org.junit.Assert.fail

class SettingsSecurityParserTest {

  @Test
  public void shouldParseSettingsSecurityFile() {
    final String expectedValue = "{MDwC4EIeb44Hdjg4Dyc8bcfkcHuxm9EMEuD0Kyj6AwHZYb898KbjOlT5VwRfKylU}"
    def settingsSecurityFile = "src/test/resources/test-files/settings-security.xml"
    final String actualValue = new SettingsSecurityParser().getMasterPassword(settingsSecurityFile)
    assert expectedValue == actualValue
  }

  @Test
  public void shouldReturnEmptyStringIfFileDoesNotContainMasterElement() {
    def settingsSecurityFile = "src/test/resources/test-files/settings-security-no-master-element.xml"
    assert "" == new SettingsSecurityParser().getMasterPassword(settingsSecurityFile)
  }

  @Test
  public void shouldThrowExceptionIfFileDoesNotExist() {
    final String settingsSecurityFile = "this file does not exist"
    final String expectedErrorMessage = "Error: Specified security Settings file: ${settingsSecurityFile} was not found"
    try {
      new SettingsSecurityParser().getMasterPassword(settingsSecurityFile)
      fail("Exception should have been thrown but was not.")
    }
    catch (FileNotFoundException fnfe) {
      assert expectedErrorMessage == fnfe.message
    }
  }
}
