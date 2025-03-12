package io.codeforall.bootcamp.main;

import io.codeforall.bootcamp.field.FieldPosition;
import io.codeforall.bootcamp.field.Field;
import io.codeforall.bootcamp.charactes.Player;
import io.codeforall.bootcamp.rooms.*;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import javax.swing.*;
import java.awt.*;

public class Game implements KeyboardHandler {
    private final Field field;
    private Player player;
    private Room[] rooms;
    private Room currentRoom;
    private Picture initBackground;
    private boolean finishGame;
    private Keyboard keyboard;
    private Music music;
    private boolean pressKeySpace;


    public Game() {
        this.field = new Field(80, 80);
        this.keyboard = new Keyboard(this);
        this.player = new Player(new FieldPosition(this.field, 40, 66));
        this.rooms = new Room[3];
        this.currentRoom = null;
        this.initBackground = new Picture(field.getPADDING(), field.getPADDING(), "resources/Backgrounds/saveTheChickens.png");
        this.finishGame = false;
        this.pressKeySpace = false;
        createKeyboardEvents();
        initRooms();
        music = new Music("resources/Music/SuperMario.wav");
        music.playLoop();
    }

    public void initRooms() {
        rooms[0] = new FirstRoom(field.getPADDING(), this.field, this.player);
        rooms[1] = new SecondRoom(field.getPADDING(), this.field, this.player);
        rooms[2] = new ThirdRoom(field.getPADDING(), this.field, this.player);
    }

    public boolean isPressKeySpace() {
        return pressKeySpace;
    }

    public void initGame() {
        initialMenu();
        gameLoop();
        restartLogic();
    }

    public void initialMenu() {
        while (!this.pressKeySpace) {
            this.initBackground.draw();
        }
        this.initBackground.delete();
    }

    public void gameLoop() {
        while (!finishGame) {
            updateRoom();
            if (currentRoom instanceof SecondRoom) {
                ((SecondRoom) currentRoom).verifyIfTouchSnakes();
            }
            if (currentRoom instanceof ThirdRoom) {
                ((ThirdRoom) currentRoom).verifyIfTouchWitch();
            }
            if (player.isWantRestart()) {
                this.finishGame = true;
            }
        }
    }

    public Music getMusic(){
        return music;
    }

    public void restartLogic() {
        while (this.player.isWinner() || !this.player.isAlive()) {
            if (player.isWantRestart()) {
                music.stop();
                Game newGame = new Game();
                newGame.initGame();
            }
        }
    }

    public void updateRoom() {
        System.out.println(player.isFinishChallenge());
        if (this.player.isFinishChallenge() || currentRoom == null) {
            if (currentRoom == null) {
                this.currentRoom = rooms[0];
                field.initField(currentRoom);
                this.player.initPlayer();
                return;
            }

            if (currentRoom instanceof FirstRoom) {
                ((FirstRoom) currentRoom).deleteKeyboardEvents();
                this.player.setFinishChallenge(false);
                this.currentRoom = rooms[1];
                field.initField(currentRoom);
                updatePlayer(40, 66);
                return;
            }
            if (currentRoom instanceof SecondRoom) {
                ((SecondRoom) currentRoom).deleteKeyboardEvents();
                this.player.setFinishChallenge(false);
                this.currentRoom = rooms[2];
                field.initField(currentRoom);
                updatePlayer(40, 66);
            }
        }
    }

    public void updatePlayer(int col, int row) {
        this.player.setFieldPosition(new FieldPosition(this.field, 40, 66));
        this.player.getPlayerIcon().delete();
        this.player.setPlayerIcon(new Picture(player.getFieldPosition().getX(), player.getFieldPosition().getY(),
                "resources/Player/kernelfront50x85.png"));
        this.player.initPlayer();
    }

    public void createKeyboardEvents() {
        KeyboardEvent keyboardEventSpace = new KeyboardEvent();
        keyboardEventSpace.setKey(KeyboardEvent.KEY_SPACE);
        keyboardEventSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventSpace);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            this.pressKeySpace = true;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }
}
