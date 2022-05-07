package models

import javax.inject.Inject

import security.Encode;

@javax.inject.Singleton
class UserDao @Inject()() {

  def lookupUser(u: User): Boolean = {

    val encode = new Encode();

    val hash: String = encode.hashpw(u.password);

    val isMatch: Boolean = encode.verify("pass", hash);

    if (u.username == "user" && isMatch) true else false
  }

}
