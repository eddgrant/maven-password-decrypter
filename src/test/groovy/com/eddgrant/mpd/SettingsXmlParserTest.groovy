package com.eddgrant.mpd

import org.junit.Test

class SettingsXmlParserTest {

  @Test
  public void shouldReturnEmptyListIfFileContainsNoServerDefinitions() {
    // Given
    final String fileContainingNoServers = "src/test/resources/test-files/settings-with-no-servers.xml"
    final String fileContainingEmptyServersElement = "src/test/resources/test-files/settings-with-no-servers.xml"
    final List<String> inputfiles = [fileContainingNoServers, fileContainingEmptyServersElement]

    // When, Then
    inputfiles.each { inputFile ->
      assert [] == new SettingsXmlParser().getServers(inputFile)
    }
  }

  @Test
  public void shouldParseMultipleServerDetailsWhenPresent() {
    // Given
    final String inputFile = "src/test/resources/test-files/settings-with-multiple-server-entries.xml"

    // When
    final List<ServerDetails> servers = new SettingsXmlParser().getServers(inputFile)

    // Then
    assert 3 == servers.size()

    (1..3).each { index ->
      assert "${index}" == servers[index - 1].id
      assert "u${index}" == servers[index - 1].username
    }

    assert "This is server 1's password {ScOluri2uPsHdurnBR4UPlCidn5Yllb/vBPkQIevKyz1pHe11A0Lpw48foQf65eT}" == servers[0].encryptedPassword
    assert "This is server 2's password {pgt7K+js3SsHULVNxo/U9mHI2gEhkUi1FmOnJ1ZDjY7r4nQwIJmXx94BCR0xwqYW}" == servers[1].encryptedPassword
    assert "This is server 3's password {mU3PuwllB5wHbbLMkJ2T9wpA7Y2EHVVR4aDIDgprkeUPduc7DONbjBMXg6ViyfOl}" == servers[2].encryptedPassword
  }
}
