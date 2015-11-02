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

    assert "This is server 1's password {MPjrTeXO+YsHfubYx4j0PZw28rVgohZ8+dT7SCtV/b8WC0fCYMEfmghCWGRCWPQI}" == servers[0].password
    assert "This is server 2's password {TGpcenEFDiEHwTO7by4Cl8abcroksbkqw6OQpwqpIvPQibq3LOe6ldAm6Hcx4buk}" == servers[1].password
    assert "This is server 3's password {awMKcT+AK78H1j6bGz0IumpS6fm8ZLfzU16/SSL4z1rKUMsIzh2rLfMMgqOnGqJ4}" == servers[2].password
  }
}
