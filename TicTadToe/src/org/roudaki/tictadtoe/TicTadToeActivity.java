package org.roudaki.tictadtoe;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.widget.Toast;

public class TicTadToeActivity extends SimpleBaseGameActivity implements
		OnClickListener {

	final private int CameraHeight = 480;
	final private int CameraWidth = 480;

	final private int GridWidth = 3;
	final private int GridHeight = 3;

	final private int STROKE_HEIGHT = 4;

	private BuildableBitmapTextureAtlas BitmapTextureAtlas;
	private ITextureRegion blankTexture;
	private ITextureRegion xTexture;
	private ITextureRegion oTexture;

	
	private ButtonSprite[][] gridSprite= new ButtonSprite[GridWidth][GridHeight];
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		final Camera camera = new Camera(0, 0, CameraWidth, CameraHeight);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new RatioResolutionPolicy(CameraWidth, CameraHeight), camera);

	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
	
		
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(TicTadToeActivity.this, "Clicked", Toast.LENGTH_LONG).show();
				}
			});
		
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.BitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 128, 128);
		this.blankTexture = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.BitmapTextureAtlas, this, "blankIcon.png");
		this.xTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.BitmapTextureAtlas, this, "X.png");
		this.oTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.BitmapTextureAtlas, this, "O.png");

		try {
			this.BitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
			this.BitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	@Override
	protected Scene onCreateScene() {
		final Scene scene = new Scene();

		final VertexBufferObjectManager VBOM = this
				.getVertexBufferObjectManager();

		float linex[] = new float[GridWidth];
		float liney[] = new float[GridHeight];

		float touchx[] = new float[GridWidth];
		float touchy[] = new float[GridHeight];

		float midTouchX = CameraWidth / GridWidth / 2;
		float midTouchY = CameraHeight / GridHeight / 2;

		float halfTouchX = blankTexture.getWidth() / 2;
		float halfTouchy = blankTexture.getHeight() / 2;
		float paddingX= midTouchX-halfTouchX;
		float paddingY= midTouchY-halfTouchy;
		
		
		
		
		for (int i = 0; i < GridWidth; i++) {
			linex[i] = CameraWidth / GridWidth * i;
			touchx[i] = linex[i] + paddingX;

		}
		for (int i = 0; i < GridHeight; i++) {
			linex[i] = CameraHeight / GridHeight * i;
			touchy[i] = liney[i] + paddingY;
		}
		scene.setBackground(new Background(0.85f, 0.85f, 0.85f));
		// draw grid

		for (int i = 1; i < GridWidth; i++) {
			final Line line = new Line(linex[i], 0, linex[i], CameraHeight,
					STROKE_HEIGHT, VBOM);
			line.setColor(0.15f,0.15f,0.15f);
			scene.attachChild(line);
		}
		for (int i = 1; i < GridHeight; i++) {
			final Line line = new Line(0, liney[i], CameraHeight, liney[i],
					STROKE_HEIGHT, VBOM);
			line.setColor(0.15f,0.15f,0.15f);
			scene.attachChild(line);
		}
		
		
		for (int i=0; i <GridWidth;i++){
			for (int j=0;j<GridHeight;j++){

				final ButtonSprite butt = new ButtonSprite(touchx[i], touchy[j],
						this.blankTexture, this.xTexture, this.oTexture, VBOM, this);
				scene.registerTouchArea(butt);
				scene.attachChild(butt);
				gridSprite[i][j]= butt;
		
			}
		}
		scene.setTouchAreaBindingOnActionDownEnabled(true);

		return scene;
	}
}
