package com.pomavau.crimson.View;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Pomavau on 15.05.16.
 */
public class FontActor extends Actor{
    private BitmapFont font;
    private String text;
    private float x;
    private float y;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
        public FontActor(BitmapFont font, String text, float x, float y)
        {
            this.font = font;
            this.text = text;
            setPosition(x, y);
        }

    public FontActor(String pathToFont, int size, Color color, String text, float x, float y)
        {
            generator = new FreeTypeFontGenerator(new FileHandle(pathToFont));
            parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = size;
            parameter.color = color;
            font = generator.generateFont(parameter);
            this.text = text;
            setPosition(x, y);
        }
    public void draw(Batch batch, float parentAlpha)
    {
        font.draw(batch, text, getX(), getY());
    }

    public void update(String text)
    {
        this.text = text;
    }

    public void update(String text, Color color)
    {
        parameter.color = color;
        font = generator.generateFont(parameter);
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
