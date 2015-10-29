package com.eddgrant.mpd

class SettingsSecurityParser {

  String parse(final String settingsSecurityFilePath) {
    final File settingsSecurityFile = new File(settingsSecurityFilePath)
    if(! settingsSecurityFile.exists()) {
      throw new FileNotFoundException(fnfMsg(settingsSecurityFilePath))
    }
    final def settingsSecurityNode = new XmlSlurper().parse(settingsSecurityFile)
    return settingsSecurityNode?.master?.text()
  }

  private String fnfMsg(final String filePath) {
    return "Error: Specified security Settings file: ${filePath} was not found"
  }

}
