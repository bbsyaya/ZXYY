package com.zhixinyisheng.user.util.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Date;

/**
 * @Class: CameraPreview
 * @Description: 自定义相机
 * @author: lling(www.cnblogs.com/liuling)
 * @Date: 2015/10/25
 */
public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback, AutoFocusCallback {
	private static final String TAG = "CameraPreview";

	private int viewWidth = 0;
	private int viewHeight = 0;

	/** 监听接口 */
	private OnCameraStatusListener listener;

	private SurfaceHolder holder;
	private Camera camera;
	private FocusView mFocusView;

	//创建一个PictureCallback对象，并实现其中的onPictureTaken方法
	private PictureCallback pictureCallback = new PictureCallback() {

		// 该方法用于处理拍摄后的照片数据
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// 停止照片拍摄
			try {
				camera.stopPreview();
			} catch (Exception e) {
			}
			// 调用结束事件
			if (null != listener) {
				listener.onCameraStopped(data);
			}
		}
	};

	// Preview类的构造方法
	public CameraPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获得SurfaceHolder对象
		holder = getHolder();
		// 指定用于捕捉拍照事件的SurfaceHolder.Callback对象
		holder.addCallback(this);
		// 设置SurfaceHolder对象的类型
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// 在surface创建时激发
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e(TAG, "==surfaceCreated==");
		if(!Utils.checkCameraHardware(getContext())) {
			Toast.makeText(getContext(), "请从手机设置里开启摄像头权限！", Toast.LENGTH_SHORT).show();
			return;
		}
		// 获得Camera对象
		camera = getCameraInstance();
		try {
			// 设置用于显示拍照摄像的SurfaceHolder对象
			camera.setPreviewDisplay(holder);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 释放手机摄像头
				camera.release();
				camera = null;
			}catch (Exception e1){

				return;
			}

		}
		updateCameraParameters();
		if (camera != null) {
			camera.startPreview();
		}
//		setFocus();
	}

	// 在surface销毁时激发
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e(TAG, "==surfaceDestroyed==");
		try {
			// 释放手机摄像头
			camera.release();
			camera = null;
		}catch (Exception e){

		}

	}

	// 在surface的大小发生改变时激发
	public void surfaceChanged(final SurfaceHolder holder, int format, int w,
							   int h) {
		Log.e(TAG, "surfaceChanged: qian" );
		// stop preview before making changes
		try {
			camera.stopPreview();
		} catch (Exception e){
			// ignore: tried to stop a non-existent preview
		}
		// set preview size and make any resize, rotate or
		// reformatting changes here
		updateCameraParameters();
		// start preview with new settings
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();

		} catch (Exception e){
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
//		setFocus();
	}

	/**
	 * 获取摄像头实例
	 * @return
	 */
	int currentId;
	private Camera getCameraInstance() {
		Camera c = null;

		try {
			int cameraCount = 0;
			Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
			cameraCount = Camera.getNumberOfCameras(); // get cameras number
			for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
				Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
				// 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//Camera.CameraInfo.CAMERA_FACING_FRONT
					try {
						c = Camera.open(camIdx);   //打开前置摄像头
						Log.e(TAG, "getCameraInstance: qian"+camIdx );
						currentId =camIdx;
					} catch (RuntimeException e) {
//						Toast.makeText(getContext(), "摄像头打开失败！", Toast.LENGTH_SHORT).show();
					}
				}
			}
			if (c == null) {
				c = Camera.open(1); // attempt to get a Camera instance
			}
		} catch (Exception e) {
//			Toast.makeText(getContext(), "摄像头打开失败！", Toast.LENGTH_SHORT).show();
		}
		return c;
	}
	private void updateCameraParameters() {
		if (camera != null) {
			Camera.Parameters p = camera.getParameters();

			setParameters(p);

			try {
				camera.setParameters(p);
			} catch (Exception e) {
//				Camera.Size previewSize = findBestPreviewSize(p);
//				p.setPreviewSize(previewSize.width, previewSize.height);
//				p.setPictureSize(previewSize.width, previewSize.height);
				p.setZoom(p.getMaxZoom());
				try {
					p.setZoom(p.getMaxZoom());
					camera.setParameters(p);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param p
	 */
	private void setParameters(Camera.Parameters p) {
//		List<String> focusModes = p.getSupportedFocusModes();
//		if (focusModes
//				.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//			p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//		}

		long time = new Date().getTime();
		p.setGpsTimestamp(time);
		// 设置照片格式
		p.setPictureFormat(PixelFormat.JPEG);
//		Camera.Size previewSize = findPreviewSizeByScreen(p);
//		p.setPreviewSize(previewSize.width, previewSize.height);
//		p.setPictureSize(previewSize.width, previewSize.height);
//		p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		if (getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
			camera.setDisplayOrientation(90);
			p.setRotation(90);
		}
		p.setZoom(p.getMaxZoom());
	}

	// 进行拍照，并将拍摄的照片传入PictureCallback接口的onPictureTaken方法
	public void takePicture() {
		if (camera != null) {
			try {
				camera.takePicture(null, null, pictureCallback);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 设置监听事件
	public void setOnCameraStatusListener(OnCameraStatusListener listener) {
		this.listener = listener;
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {

	}

	public void start() {
		if (camera != null) {
			camera.startPreview();
		}
	}

	public void stop() {
		if (camera != null) {
			camera.stopPreview();
		}
	}

	/**
	 * 相机拍照监听接口
	 */
	public interface OnCameraStatusListener {
		// 相机拍照结束事件
		void onCameraStopped(byte[] data);
	}





	/**
	 * 设置聚焦的图片
	 * @param focusView
	 */
	public void setFocusView(FocusView focusView) {
		this.mFocusView = focusView;
	}


}