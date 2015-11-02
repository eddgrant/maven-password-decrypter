package com.eddgrant.mpd

class SettingsXmlParser extends AbstractFileParser {

  public List<?> getServers(final String settingsXmlFilePath) {
    final File settingsFile = getFile(settingsXmlFilePath)
    final def settingsNode = new XmlSlurper().parse(settingsFile)
    final List<ServerDetails> servers = []
    if(settingsNode?.servers != "") {
      settingsNode?.servers?.children()?.each { server ->

        servers.add(new ServerDetails(id: server?.id.text(),
                                      username: server?.username.text(),
                                      encryptedPassword: server?.password?.text()))
      }
    }
    return servers
  }

  @Override
  protected String fnfMsg(final String filePath) {
    return "Error: Specified Settings file: ${filePath} was not found"
  }
}
