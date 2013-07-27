package com.example.yuanfen;



import android.R.integer;
import android.R.interpolator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class MatchActivity extends Activity implements OnClickListener {
	public static final int P90 = 1;//��90�� ����Ļ���
	public static final int N90 = 2;//��90�� ����Ļ�Ҳ�
	protected static final int ANIMATIONEND = 3; //��������
	protected static final int ANIMATIONSTART = 4;//������ʼ
    
	//ͼ1��2��3��4
	private static ImageView mImageView1;
	private static ImageView mImageView2;
	private static ImageView mImageView3;
	private static ImageView mImageView4;
	
	//��Բ
	private static ImageView ivOval;
	
	//x,y���� ������ת�Ĺ켣
	private float mCenterX,mCenterY;
	
	//��ת�Ƕ�ƫ�� ��������ֹͣʱ����ͼƬ�����Ϸ�
	private float degree=0;
	
	//ͼƬid Ҳ�����ѡ�к��ͼƬid
	private static int id=0;
	
	//��ת���ɹ���ʧ�����ֲ���
	private static RelativeLayout rlSucceed,rlRotate,rlFailed;
	
	// �ɹ�������һ�Σ����� ��ť
	private Button bSucceed,bAgain,bBack;
	
	private static ImageView ivRight;
	
	//�Ƿ��һ�ε���
	protected boolean firstInvoke = true;;
	
	//��ת�ĽǶ� ����  360*n  nΪ��ת��Ȧ��
	private final static float degreeC = 2160;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        
        rlRotate = (RelativeLayout)findViewById(R.id.rlRotate);
        mImageView1=(ImageView)findViewById(R.id.image1);
        mImageView2=(ImageView)findViewById(R.id.image2);
        mImageView3=(ImageView)findViewById(R.id.image3);
        mImageView4=(ImageView)findViewById(R.id.image4);
        ivOval=(ImageView)findViewById(R.id.ivOval);
        
        rlSucceed = (RelativeLayout) findViewById(R.id.rlSucceed);
        bSucceed = (Button) findViewById(R.id.bSucceed);
        bSucceed.setOnClickListener(this);
        ivRight = (ImageView)findViewById(R.id.ivRight);
        rlSucceed.setVisibility(View.GONE);//���ش˲���
        
        rlFailed = (RelativeLayout)findViewById(R.id.rlFailed);
        bAgain = (Button)findViewById(R.id.bAgain);
        bAgain.setOnClickListener(this);
        bBack = (Button)findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
        rlFailed.setVisibility(View.GONE);//���ش˲���
        
        
        
        //�����Ի�ȡ�ؼ������ʱ�� ���߳� ��������
        ViewTreeObserver vto = mImageView1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	if(firstInvoke )//ֻ����һ��
            	{
            		firstInvoke = false;
            		
            		new Thread(new Runnable(){

        				@Override
        				public void run() {
        					// TODO Auto-generated method stub
        					
        					try {
        						Thread.sleep(50);
        					} catch (InterruptedException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        					}  
        					
        					Message message = new Message();   
        			        message.what = MatchActivity.ANIMATIONSTART;  
        			        MatchActivity.this.myHandler2.sendMessage(message); 
        					
        				}
            			 
            		 }).start();  //�����߳�
//            		startAnimation();
            	}
            }
        });
    }
    
    
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
	}



	@Override
    public boolean onTouchEvent(MotionEvent event) {
    	// TODO Auto-generated method stub
//    	startAnimation();
    		
    	return super.onTouchEvent(event);
    }
    
    private void startAnimation(){
    	
    	//���ݵ�ǰ��ʱ������������ͼƬ��ѡ��
    	long time=System.currentTimeMillis();
    
    	switch ((int)time%4) {
		case 0:
			id=1;
			degree=-90;
			break;
		case 1:
			id=4;
			degree=0;
			break;
		case 2:
			id=3;
			degree=90;
			break;
		case 3:
			id=2;
			degree=180;
			break;

		default:
			break;
		}
    	
    	//mCenter�ǿؼ�������
    	
    	mCenterX = mImageView1.getWidth()/2.0f;
    	mCenterY = mImageView1.getHeight()/2.0f;
    	
    	
    	//�����������ʼ�Ƕȣ������Ƕȣ��ؼ����ģ��Լ�ͼƬ���
    	Rotate3d rotateAnimation = new Rotate3d(0, -90+degree-degreeC, 0, 0, mCenterX, mCenterY,1);
    	Rotate3d rotateAnimation2 = new Rotate3d(90, -90+degree+90-degreeC, 0, 0, mCenterX, mCenterY,2);
    	Rotate3d rotateAnimation3 = new Rotate3d(180, -90+degree+180-degreeC, 0, 0, mCenterX, mCenterY,3);
    	Rotate3d rotateAnimation4 = new Rotate3d(270, -90+degree+270-degreeC, 0, 0, mCenterX, mCenterY,4);
    	       
    	rotateAnimation.setFillAfter(false);//����������ȡ��ԭ��λ��
    	rotateAnimation.setDuration(8000);//����ʱ��
    	rotateAnimation2.setFillAfter(false);
    	rotateAnimation2.setDuration(8000);
    	rotateAnimation3.setFillAfter(false);
    	rotateAnimation3.setDuration(8000);
    	rotateAnimation4.setFillAfter(false);
    	rotateAnimation4.setDuration(8000);
       
        mImageView1.startAnimation(rotateAnimation);
        mImageView2.startAnimation(rotateAnimation2);
        mImageView3.startAnimation(rotateAnimation3);
        mImageView4.startAnimation(rotateAnimation4);
       

        rotateAnimation.setAnimationListener(new AnimationListener(){
        	@Override
        	public void onAnimationEnd(Animation animation) {
        		// TODO Auto-generated method stub
        		
        		//�������� 
        		Message message = new Message();   
    	        message.what = MatchActivity.ANIMATIONEND;  
    	        MatchActivity.myHandler.sendMessage(message);           	
        		
        	}
        	@Override
        	public void onAnimationRepeat(Animation animation) {
        		// TODO Auto-generated method stub
        		
        	}
        	@Override
        	public void onAnimationStart(Animation animation) {
        		// TODO Auto-generated method stub
        		
        		//��������ǰ ����ÿ��ͼƬ������ �������� ���ŷ���
        		mImageView1.setImageResource(R.drawable.back);
        		mImageView2.setImageResource(R.drawable.back);
        		mImageView3.setImageResource(R.drawable.pic3);
        		mImageView4.setImageResource(R.drawable.pic4);
        		//���泯�ϵ�ͼƬҪ�����㼶
        		mImageView3.bringToFront();
        		mImageView4.bringToFront();
        		
        	}
        });
}
    
    Handler myHandler2 = new Handler()
    {
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
    		case MatchActivity.ANIMATIONSTART:
          	  startAnimation();
          	  break;

			default:
				break;
			}
			
		}
    };
    
    
	 @SuppressLint("HandlerLeak")
	static Handler myHandler = new Handler() {  
	         public void handleMessage(Message msg) { 
	        	 	int currentID = msg.getData().getInt("id");
	              switch (msg.what) { 

	              case MatchActivity.ANIMATIONEND:
	            	  rlRotate.setVisibility(View.GONE);
	            	  if(id==4)
	            	  {
	            		  rlFailed.setVisibility(View.VISIBLE);
	            	  }
	            	  else {
	            		  
	            		  rlSucceed.setVisibility(View.VISIBLE);
	            		  if(id==1)
	            			  ivRight.setImageResource(R.drawable.rpic1);
	            		  if(id==2)
	            			  ivRight.setImageResource(R.drawable.rpic2);
	            		  if(id==3)
	            			  ivRight.setImageResource(R.drawable.rpic3);
	            		  
	            			  
	            		  
					}
	            	  
	            	  
	            	 System.out.println("AnimationEnd---->id:"+id);
	            	  break;
	              case MatchActivity.P90://ת����� �ı���
	            	  
	            	  
	            	  
	            	 
	            	  if(currentID==1)
	            		  
	            		  mImageView1.setImageResource(R.drawable.back);
	        		  if(currentID==2)
	        			  
	        			  mImageView2.setImageResource(R.drawable.back);
	        		  if(currentID==3)
	            		  
	            		  mImageView3.setImageResource(R.drawable.back);
	        		  if(currentID==4)
	        			  
	        			  mImageView4.setImageResource(R.drawable.back);
	                	   break;
	              case MatchActivity.N90:
	            	  
	            	  if(currentID==1)
	            	  {
	            		  ivOval.bringToFront();
	            		  mImageView4.bringToFront();
	            		  mImageView1.bringToFront();
	            		  mImageView1.setImageResource(R.drawable.pic1);
	            	  }
	            	  if(currentID==2)
	            	  {  
	            		  ivOval.bringToFront();
	            		  mImageView1.bringToFront();
	            		  mImageView2.bringToFront();
	        			  mImageView2.setImageResource(R.drawable.pic2);
	            	  }
	            	  if(currentID==3)
	            	  {   
	            		  ivOval.bringToFront();
	            		  mImageView2.bringToFront();
	            		  mImageView3.bringToFront();
	            		  mImageView3.setImageResource(R.drawable.pic3);
	            	  }
	            	  if(currentID==4)
	            	  {
	            		  ivOval.bringToFront();
	            		  mImageView3.bringToFront();
	            		  mImageView4.bringToFront();
	        			  mImageView4.setImageResource(R.drawable.pic4);
	            	  }
	            	  break;
	               default:
	            	   break;	   
	                	   
	              }   
	              super.handleMessage(msg);   
	         }   
	    };

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==bSucceed.getId())
		{
			finish();
		}
		if(v.getId()==bAgain.getId())
		{
			rlFailed.setVisibility(View.GONE);
			rlRotate.setVisibility(View.VISIBLE);
			startAnimation();
			
		}
		if(v.getId()==bBack.getId())
		{
			finish();
		}
	}  
}