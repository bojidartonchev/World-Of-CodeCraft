package Interfaces;


import java.awt.*;

public interface IEnemy {

     boolean isDead();

     int getDamage();

     void hit(int damage);

     void update();
}
