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
		
		
		
		if(degreeSin>0.95)//�˶������  ����73��
		{
//			degreeX=(float) Math.cos(Math.toRadians(degrees));
			if(degreeCos<0)//��ʾԶ������
			{
			Message message = new Message();   
	        message.what = MatchActivity.P90;  
	        
	        Bundle bundle = new Bundle();    
	        bundle.putInt("id", id);
            //��Bundle��put����  
	        message.setData(bundle);//mes����Bundle��������  
	        MatchActivity.myHandler.sendMessage(message); 
			}
		}
		
		if(degreeSin<-0.85)//�ұ�  ����60������
		{
//			degreeX=(float) Math.cos(Math.toRadians(degrees));
//			if(degreeCos<-0.25)//��ʾ�������� С�� 70������
			if(degreeCos<-0.3)
			{
		
			Message message = new Message();   
	        message.what = MatchActivity.N90;  
	        
	        Bundle bundle = new Bundle();    
	        bundle.putInt("id", id);
            //��Bundle��put����  
	        message.setData(bundle);//mes����Bundle��������  
	        MatchActivity.myHandler.sendMessage(message); 
			}
			
		}
	
			
			mCamera.save();
			
			
//			//����1����ת�����б   ��ת����
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
			
			//����2����ת���Ϊ��Բ  ͬʱ��������ת
//			mCamera.translate(-3*centerX*degreeY, 0, 3*centerX*degreeX);//ƽ��*3
//			mCamera.rotateY(degrees);
			
			
			//����3��matrix �γɹ�ת��� ��  skew������תЧ��
			
//			mCamera.rotate(0, degrees, 0);//��y����ת  ��������2.3.1���޷�ʹ��
			mCamera.rotateY(degrees);
			mCamera.translate(0,0, 2*centerX);//��z�᷽��ƽ��  Ч���� ԶС����
			
			
			mCamera.getMatrix(matrix);
			mCamera.restore();

			matrix.preTranslate(-centerX, -centerY);
			
			//matrix ������Ϊԭ�� ��y����������  Ч����ʹ�켣������
			matrix.preSkew(0, -0.5f*degreeSin,centerX,centerY);
			//matrix ��ԭ����x���y�᷽���˶�  Ч����xyƽ���һ����Բ  xΪ���� yΪ����
			matrix.postTranslate(centerX-centerX*degreeSin, centerY-centerY*degreeCos);//���ö�������������
			
	}
	
}
