package com.example.yuanfen;

import android.R.interpolator;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Message;
import android.text.StaticLayout;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3d extends Animation{
	private float mFromDegree;
	private float mToDegree;
	private float mCenterX;
	private float mCenterY;
	private float mLeft;
	private float mTop;
	private Camera mCamera;
	private static final String TAG = "Rotate3d";
	private int id;

	public Rotate3d(float fromDegree, float toDegree, float left, float top,
			float centerX, float centerY,int id) {
		this.mFromDegree = fromDegree;
		this.mToDegree = toDegree;
		this.mLeft = left;
		this.mTop = top;
		this.mCenterX = centerX;
		this.mCenterY = centerY;
		
		this.id = id;

	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float FromDegree = mFromDegree;
		float degrees = FromDegree + (mToDegree - mFromDegree)
				* interpolatedTime;
		final float centerX = mCenterX;
		final float centerY = mCenterY;
		final Matrix matrix = t.getMatrix();
		float degreeCos = 0;
		float degreeSin = 0;
	
//		degreeX = -20*((float) Math.cos(Math.toRadians(degrees))+1)/2;
		
	
		degreeSin=(float) Math.sin(Math.toRadians(degrees));
		degreeCos=(float) Math.cos(Math.toRadians(degrees));
		
		

//		Log.d(TAG, ">>>current degrees:" + degrees+" degreeX: "+degreeX+" degreeY: "+degreeY);
		
		
		
		if(degreeSin>0.95)//运动到左边  大于73度
		{
//			degreeX=(float) Math.cos(Math.toRadians(degrees));
			if(degreeCos<0)//表示远离中心
			{
			Message message = new Message();   
	        message.what = MatchActivity.P90;  
	        
	        Bundle bundle = new Bundle();    
	        bundle.putInt("id", id);
            //往Bundle中put数据  
	        message.setData(bundle);//mes利用Bundle传递数据  
	        MatchActivity.myHandler.sendMessage(message); 
			}
		}
		
		if(degreeSin<-0.85)//右边  大于60度左右
		{
//			degreeX=(float) Math.cos(Math.toRadians(degrees));
//			if(degreeCos<-0.25)//表示靠近中心 小于 70度左右
			if(degreeCos<-0.3)
			{
		
			Message message = new Message();   
	        message.what = MatchActivity.N90;  
	        
	        Bundle bundle = new Bundle();    
	        bundle.putInt("id", id);
            //往Bundle中put数据  
	        message.setData(bundle);//mes利用Bundle传递数据  
	        MatchActivity.myHandler.sendMessage(message); 
			}
			
		}
	
			
			mCamera.save();
			
			
//			//方案1：公转轨道倾斜   自转补偿
////			mCamera.rotate(-30, degrees, 0);
//			mCamera.rotateX(-30);
//			mCamera.rotateY(degrees);
//			mCamera.translate(0, 0, 3*centerX);
//			mCamera.rotateX(30*degreeX);
//			mCamera.rotateZ(-30*degreeY);
			
			
//			mCamera.rotate(-30, degrees, 0);
//			
//			mCamera.translate(0, 0, 3*centerX);
//			mCamera.rotateX(30*degreeX);
//			
			
			//方案2：公转轨道为椭圆  同时伴随着自转
//			mCamera.translate(-3*centerX*degreeY, 0, 3*centerX*degreeX);//平移*3
//			mCamera.rotateY(degrees);
			
			
			//方案3：matrix 形成公转轨道 和  skew修正自转效果
			
//			mCamera.rotate(0, degrees, 0);//绕y轴自转  本方法在2.3.1上无法使用
			mCamera.rotateY(degrees);
			mCamera.translate(0,0, 2*centerX);//在z轴方向平移  效果是 远小近大
			
			
			mCamera.getMatrix(matrix);
			mCamera.restore();

			matrix.preTranslate(-centerX, -centerY);
			
			//matrix 以中心为原点 在y方向上拉伸  效果是使轨迹更美观
			matrix.preSkew(0, -0.5f*degreeSin,centerX,centerY);
			//matrix 的原点在x轴和y轴方向运动  效果是xy平面的一个椭圆  x为长轴 y为短轴
			matrix.postTranslate(centerX-centerX*degreeSin, centerY-centerY*degreeCos);//设置动画的中心坐标
			
	}
	
}
