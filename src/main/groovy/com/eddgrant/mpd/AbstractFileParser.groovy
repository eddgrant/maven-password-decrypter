package com.eddgrant.mpd

abstract class AbstractFileParser {

  protected File getFile(String filePath) {
    final File file = new File(filePath)
    if (!file.exists()) {
      throw new FileNotFoundException(fnfMsg(filePath))
    }
    return file
  }

  protected abstract String fnfMsg(final String filepath)

}
