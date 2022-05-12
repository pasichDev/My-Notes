package com.pasich.mynotes.Utils.Check;

public class CheckNamesFoldersUtils {

  /** Reservation of folder names that cannot be used by a regular user */
  public final String[] folderSystem = {"trash", "VoiceNotes"};

  /**
   * Method that checks if the name matches reserved folder names
   *
   * @param nameFolder - name to check
   * @return - (boolean) if true then match, if false then not
   */
  public boolean getMatchFolders(String nameFolder) {
    boolean check = false;
    for (String sys : folderSystem) {
      if (sys.equals(nameFolder)) {
        check = true;
        break;
      }
    }
    return check;
  }
}
