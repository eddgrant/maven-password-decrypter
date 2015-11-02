package com.eddgrant.mpd

import groovy.transform.ToString

@ToString(excludes = ['password'])
class ServerDetails {

  String id
  String username
  String password

}