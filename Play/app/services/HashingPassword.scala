package services

import org.mindrot.jbcrypt.BCrypt


/**  * Created by sumit on 4/30/16.  *  Hashing Password Using BCrypt  *  */
object HashingPassword {
  def getHash(str: String) : String = {
    BCrypt.hashpw(str, BCrypt.gensalt())
  }
  def checkHash(str: String, strHashed: String): Boolean = {
    BCrypt.checkpw(str,strHashed)
  }
}
