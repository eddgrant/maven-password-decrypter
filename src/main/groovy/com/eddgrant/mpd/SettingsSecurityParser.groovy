package com.eddgrant.mpd

class SettingsSecurityParser extends AbstractFileParser {

  String getMasterPassword(final String settingsSecurityFilePath) {
    final File settingsSecurityFile = getFile(settingsSecurityFilePath)
    final def settingsSecurityNode = new XmlSlurper().parse(settingsSecurityFile)
    return settingsSecurityNode?.master?.text()
  }

  protected String fnfMsg(final String filePath) {
    return "Error: Specified security Settings file: ${filePath} was not found"
  }

}
