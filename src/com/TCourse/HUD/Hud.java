// Heads-Up Display contains relevant information for player:
// bar on the bottom, course that just taken, time, etc.
package com.TCourse.HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TCourse.Entity.Player;
import com.TCourse.Main.GamePanel;
import com.TCourse.Manager.Content;
import com.TCourse.Manager.JukeBox;

public class Hud {

  private int yOffset;

  private BufferedImage bar;
  private BufferedImage boat;
  private BufferedImage axe;
  private BufferedImage pickaxe;
  private BufferedImage key;

  private Player player;

  private int creditUnit;
  private int countTicks;
  private int countTicks2;
  private int semester;
  private int currentSemester;
  private String courseName;
  private String[] names;

  private Font font;
  private Color textColor;

  // this enum will be used
  // when display the course that just taken
  public enum ListCourses {
    MAT1("MAT 1", "MATEMATIKA 1"),
    FIS1("FIS 1", "FISIKA 1"),
    KIM("KIM", "KIMIA DASAR"),
    PANCASILA("PANCASILA", "PANCASILA"),
    BIN("BIN", "BAHASA INDONESIA"),
    DASPROG("DASPROG", "DASAR PEMROGRAMAN"),
    MAT2("MAT 2", "MATEMATIKA 2"),
    FIS2("FIS 2", "FISIKA 2"),
    AGAMA("AGAMA", "AGAMA"),
    KWN("KWN", "KEWARGANEGARAAN"),
    BIG("BIG", "BAHASA INGGRIS"),
    STRUKDAT("STRUKDAT", "STRUKTUR DATA"),
    SISDIG("SISDIG", "SISTEM DIGITAL"),
    MATDIS("MATDIS", "MATEMATIKA DISKRIT"),
    ALIN("ALIN", "ALJABAR LINEAR"),
    KOMNUM("KOMNUM", "KOMPUTASI NUMERIK"),
    PBO("PBO", "PEMROGRAMAN BERORIENTASI OBJEK"),
    ORKOM("ORKOM", "ORGANISASI KOMPUTER"),
    SBD("SBD", "SISTEM BASIS DATA"),
    PROBSTAT("PROBSTAT", "PROBABILITAS DAN STATISTIKA"),
    PAA("PAA", "PERANCANGAN DAN ANALISIS ALGORITMA"),
    APSI("APSI", "ANALISIS DAN PERANCANGAN SISTEM INFORMASI"),
    KB("KB", "KECERDASAN BUATAN"),
    MBD("MBD", "MANAJEMEN BASIS DATA"),
    SISOP("SISOP", "SISTEM OPERASI"),
    IMK("IMK", "INTERAKSI MANUSIA DAN KOMPUTER"),
    TGO("TGO", "TEORI GRAF DAN OTOMATA"),
    PBKK("PBKK", "PEMROGRAMAN BERBASIS KERANGKA KERJA");

    private String shortName;
    private String fullName;
    ListCourses(String a, String b) {
      shortName= a;
      fullName = b;
    }
    public static String getFullName(String s) {
      for (ListCourses lc:ListCourses.values()) {
        if (s.equals(lc.shortName)) return lc.fullName;
      }
      return null;
    }

  }

  public Hud(Player p, int b) {

    player = p;
    creditUnit = b;
    yOffset = GamePanel.HEIGHT;

    bar = Content.BAR[0][0];
    axe = Content.ITEMS[0][0];
    pickaxe = Content.ITEMS[0][1];
    boat = Content.ITEMS[0][2];
    key = Content.ITEMS[0][3];


    font = new Font("Arial", Font.PLAIN, 10);
    textColor = new Color(47, 64, 126);

    semester = -1;
    currentSemester = 1;

  }

  // check if the course has taken
  public void alreadyTaken(String s) {
    countTicks = 0;
    courseName = s;
  }

  // check if the player has not finished certain semester
  public void hasNotFinished(int i) {
    countTicks2 = 0;
    semester = i;
  }

  // set total credit that will be shown in bar
  public void setCreditUnit(int i) {
    creditUnit = i;
  }

  public void draw(Graphics2D g) {

    // draw hud
    g.drawImage(bar, 0, yOffset, null);

    // draw current credit
    g.setColor(textColor);
    g.fillRect(8, yOffset + 6, (int)(28.0 * player.currentCredit() / creditUnit), 4);

    // draw total credit
    g.setColor(textColor);
    g.setFont(font);
    String s = player.currentCredit() + "/" + creditUnit;
    Content.drawString(g, s, 38, yOffset + 3);

    // draw items
    if (player.hasBoat()) g.drawImage(boat, 100, yOffset, null);
    if (player.hasAxe()) g.drawImage(axe, 88, yOffset, null);
    if (player.hasPickaxe()) g.drawImage(pickaxe, 112, yOffset, null);
    if (player.hasKey()) g.drawImage(key, 76, yOffset, null);

    // draw time
    int minutes = (int) (player.getTicks() / 1800);
    int seconds = (int) ((player.getTicks() / 30) % 60);
    if (minutes < 10) {
      if (seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 85, 3);
      else Content.drawString(g, "0" + minutes + ":" + seconds, 85, 3);
    }
    else {
      if (seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 85, 3);
      else Content.drawString(g, minutes + ":" + seconds, 85, 3);
    }

    // draw when player try to open gate with wrong key
    if (player.wrongKey()) {
      for (int i = 0; i < 90; i++) {
        Content.drawString(g, "WRONG KEY", 28, 16);
      }
      player.setWrongKey();
    }

    // draw course name when player just taken the course
    countTicks++;
    if (countTicks <= 50 && player.numCourses() > 0) {
      String stringName = ListCourses.getFullName(courseName);
      if (stringName != null) {
        int y = 16;
        String delims = "\\s+";
        for (int i = 0; i < stringName.length(); i++) {
          names = stringName.split(delims);
        }
        for (int i = 0; i < names.length; i++) {
          if ((i !=  names.length - 1) && (names[i].length() + names[i+1].length() < 12)) {
            Content.drawString(g, names[i] + " " + names[i+1], 16, y);
            i++;
          }
          else {
            if (names[i].equals("KEWARGANEGARAAN")) Content.drawString(g, names[i], 5, y);
            else Content.drawString(g, names[i], 16, y);
          }
          y += 12;
        }
      }
      else
        Content.drawString(g, courseName, 16, 16);
    }
    
    // draw when player try to take next semester's course
    // but doesn't eligible or doesn't finish a certain semester
    countTicks2++;
    if (semester > 0 && countTicks2 <= 30) {
      Content.drawString(g, "Please finish", 12, 16);
      Content.drawString(g, "SEMESTER " + semester, 24, 32);
      Content.drawString(g, "first", 44, 48);
    }
    
    // draw when player has just finished a certain semester
    if (currentSemester == 4 && countTicks >= 50 && countTicks <= 90 && player.numCourses() > 18 && player.finishedSemester4()) {
      JukeBox.play("finish_semester");
      Content.drawString(g, "You have", 30, 16);
      Content.drawString(g, "finished", 30, 30);
      Content.drawString(g, "SEMESTER 4", 24, 44);
      if (countTicks == 90) currentSemester = 5;
    }
    else if (currentSemester == 3 && countTicks >= 50 && countTicks <= 90 && player.numCourses() > 12 && player.finishedSemester3()) {
      JukeBox.play("finish_semester");
      Content.drawString(g, "You have", 30, 16);
      Content.drawString(g, "finished", 30, 30);
      Content.drawString(g, "SEMESTER 3", 24, 44);
      if (countTicks == 90) currentSemester = 4;
    }
    else if (currentSemester == 2 && countTicks >= 50 && countTicks <= 90 && player.numCourses() > 6 && player.finishedSemester2()) {
      JukeBox.play("finish_semester");
      Content.drawString(g, "You have", 30, 16);
      Content.drawString(g, "finished", 30, 30);
      Content.drawString(g, "SEMESTER 2", 24, 44);
      if (countTicks == 90) currentSemester = 3;
    }
    else if (currentSemester == 1 && countTicks >= 50 && countTicks <= 90 && player.numCourses() > 0 && player.finishedSemester1()) {
      JukeBox.play("finish_semester");
      Content.drawString(g, "You have", 30, 16);
      Content.drawString(g, "finished", 30, 30);
      Content.drawString(g, "SEMESTER 1", 24, 44);
      if (countTicks == 90) currentSemester = 2;
    }
    
  }

}
