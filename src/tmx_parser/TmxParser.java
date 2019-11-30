package tmx_parser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//@author omkardev
//github = https://github.com/OmkarDev/TMX-Parser-Java

class TmxParser {

	private TMX tmx;
	public int size;
	public int map_cols, map_rows, img_cols, img_rows;
	public ArrayList<Rect> objs = new ArrayList<Rect>();
	public BufferedImage image;
	public BufferedImage tiles[][];

	public TmxParser(String path) throws Exception {
		tmx = new TMX(path);
		image = ImageIO.read(new File(tmx.image_Path));
		size = tmx.tilewidth;
		map_cols = (int) (tmx.mapwidth);
		map_rows = (int) (tmx.mapheight);
		img_cols = image.getWidth() / size;
		img_rows = image.getHeight() / size;
		tiles = new BufferedImage[map_rows][map_cols];
		for (int i = 0; i < tmx.tiles.length; i++) {
			for (int j = 0; j < tmx.tiles[0].length; j++) {
				for (int k = 0; k < tmx.tiles[0][0].length; k++) {
					for (int x = 0; x < img_rows; x++) {
						for (int y = 0; y < img_cols; y++) {
							if ((y + x * img_cols) + 1 == tmx.tiles[i][j][k]) {
								tiles[j][k] = image.getSubimage((y) * size, (x) * size, size, size);
							}
						}
					}
				}
			}
		}
		objs = tmx.objs;
	}

}
