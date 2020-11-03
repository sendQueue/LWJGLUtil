package de.vinii.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * @author sendQueue <Vinii>
 *
 *         Further info at Vinii.de or github@vinii.de, file created at 20.10.2020. 
 *         Use is only authorized if given credit!
 * 
 */
public class GL11Util {
	
	/**
	 * Draws rect with rounded corners, how it's made:
	 * https://vinii.de/github/LWJGLUtil/roundedRect.png
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param radius
	 * @param color
	 */
	public static void drawRoundedRect(final float x, final float y, final float width, final float height,
			final float radius, final int color) {
		float x2 = x + ((radius / 2f) + 0.5f);
		float y2 = y + ((radius / 2f) + 0.5f);
		float calcWidth = (width - ((radius / 2f) + 0.5f));
		float calcHeight = (height - ((radius / 2f) + 0.5f));
		// top (pink)
		relativeRect(x2 + radius / 2f, y2 - radius / 2f - 0.5f, x2 + calcWidth - radius / 2f, y + calcHeight - radius / 2f,
				color);
		// bottom (yellow)
		relativeRect(x2 + radius / 2f, y2, x2 + calcWidth - radius / 2f, y2 + calcHeight + radius / 2f + 0.5f, color);
		// left (red)
		relativeRect((x2 - radius / 2f - 0.5f), y2 + radius / 2f, x2 + calcWidth, y2 + calcHeight - radius / 2f, color);
		// right (green)
		relativeRect(x2, y2 + radius / 2f + 0.5f, x2 + calcWidth + radius / 2f + 0.5f, y2 + calcHeight - radius / 2f,
				color);

		// left top circle
		polygonCircle(x, y - 0.15, radius * 2, 360, color);
		// right top circle
		polygonCircle(x + calcWidth - radius + 1.0, y - 0.15, radius * 2, 360, color);
		// left bottom circle
		polygonCircle(x, y + calcHeight - radius + 1, radius * 2, 360, color);
		// right bottom circle
		polygonCircle(x + calcWidth - radius + 1, y + calcHeight - radius + 1, radius * 2, 360, color);
	}

	/**
	 * Draws a rect, as in {@link Gui}#drawRect.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param color
	 */
	public static void relativeRect(final float left, final float top, final float right, final float bottom,
			final int color) {

		final Tessellator tessellator = Tessellator.getInstance();
		final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		glColor(color);

		worldRenderer.begin(7, DefaultVertexFormats.POSITION);
		worldRenderer.pos(left, bottom, 0).endVertex();
		worldRenderer.pos(right, bottom, 0).endVertex();
		worldRenderer.pos(right, top, 0).endVertex();
		worldRenderer.pos(left, top, 0).endVertex();

		tessellator.draw();

		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	/**
	 * Draws a polygon circle
	 * 
	 * @param x
	 * @param y
	 * @param sideLength
	 * @param amountOfSides
	 * @param filled
	 * @param color
	 */
	public static final void polygonCircle(final double x, final double y, double sideLength, final double degree,
			final int color) {
		sideLength *= 0.5;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GlStateManager.disableAlpha();

		glColor(color);

		GL11.glLineWidth(1);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		// since its filled, otherwise GL_LINE_STRIP
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		for (double i = 0; i <= degree; i++) {
			final double angle = i * (Math.PI * 2) / degree;

			GL11.glVertex2d(x + (sideLength * Math.cos(angle)) + sideLength,
					y + (sideLength * Math.sin(angle)) + sideLength);
		}

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);

		GlStateManager.enableAlpha();

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	/**
	 * 
	 * Draws a horizontal gradient rect
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param col1
	 * @param col2
	 */
	public static void drawHorizontalGradient(final float x, final float y, final float width, final float height,
			final int leftColor, final int rightColor) {
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glShadeModel(7425);

		GL11.glPushMatrix();
		GL11.glBegin(7);

		glColor(leftColor);

		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y + height);

		glColor(rightColor);

		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x + width, y);

		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glShadeModel(7424);
	}
  
	/**
	 * Draws a vertical gradient rect
	 * 
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 * @param topColor
	 * @param bottomColor
	 */
	public static void drawVerticalGradient(final float x, final float y, final float width, final float height,
			final int topColor, final int bottomColor) {
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glShadeModel(7425);
		
		GL11.glPushMatrix();
		GL11.glBegin(7);
		
		glColor(topColor);
		
		GL11.glVertex2f(x, y + height);
		GL11.glVertex2f(x + width, y + height);
		
		glColor(bottomColor);
		
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x, y);
		
		GL11.glEnd();
		GL11.glPopMatrix();
		
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glShadeModel(7424);
	}
  
  	/**
	 * Sets color from hex
	 * 
	 * @param hex
	 */
	public static void glColor(final int hex) {
		//shifting
		final float alpha = (hex >> 24 & 255) / 255f;
		final float red = (hex >> 16 & 255) / 255f;
		final float green = (hex >> 8 & 255) / 255f;
		final float blue = (hex & 255) / 255f;

		GL11.glColor4f(red, green, blue, alpha);
	}
}
