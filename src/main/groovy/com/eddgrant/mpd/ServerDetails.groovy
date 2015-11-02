package com.eddgrant.mpd

import groovy.transform.ToString

@ToString(excludes = ['decryptedPassword'])
class ServerDetails {

  String id
  String username
  String encryptedPassword
  String decryptedPassword
}