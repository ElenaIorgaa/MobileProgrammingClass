package com.example.sensorsurvey

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.Surface
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs


class MainActivity : AppCompatActivity(),SensorEventListener{
    private var mSpotTop: ImageView? = null
    private var mSpotBottom: ImageView? = null
    private var mSpotLeft: ImageView? = null
    private var mSpotRight: ImageView? = null
    var mSensorManager: SensorManager? = null
    var mSensorProximity : Sensor? = null
    var mSensorLight: Sensor? = null
    var mPressure: Sensor? = null
    var mHumidity: Sensor? = null
    var mAcceleratie: Sensor? = null
    var mCampMagnetic: Sensor? = null
    private lateinit var mTextSensorProximity: TextView
    private lateinit var mTextSensorLight: TextView
    private lateinit var mTextPressure: TextView
    private lateinit var mTextHumidity: TextView
    private lateinit var mTextAcceleratie: TextView
    private lateinit var mTextCampMagnetic: TextView
    private lateinit var mTextAzimut: TextView
    private lateinit var mTextRoll: TextView
    private lateinit var mTextPitch: TextView
    private var mDisplay: Display? = null
    var mAccelerometerData = FloatArray(3)
    var mMagnetometerData = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_main)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        mTextSensorLight  = findViewById(R.id.label_light)
        mTextSensorProximity = findViewById(R.id.label_proximity)
        mSensorProximity = mSensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        mSensorLight = mSensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
        mPressure = mSensorManager?.getDefaultSensor(Sensor.TYPE_PRESSURE)
        mHumidity = mSensorManager?.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        mAcceleratie = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mCampMagnetic = mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        mTextPressure = findViewById(R.id.label_light2)
        mTextHumidity = findViewById(R.id.label_light3)
        mTextAcceleratie = findViewById(R.id.label_light4)
        mTextCampMagnetic = findViewById(R.id.label_light5)
        mTextAzimut = findViewById(R.id.label_light6)
        mTextRoll = findViewById(R.id.label_light7)
        mTextPitch = findViewById(R.id.label_light8)

        mSpotTop = findViewById(R.id.spot_top);
        mSpotBottom = findViewById(R.id.spot_bottom);
        mSpotLeft = findViewById(R.id.spot_left);
        mSpotRight = findViewById(R.id.spot_right);

        val sensor_error : String = resources.getString(R.string.error_no_sensor)
        if (mSensorLight == null) {
            mTextSensorLight.text = sensor_error
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.text = sensor_error;
        }
        if(mPressure == null)
        {
            mTextPressure.text = sensor_error
        }
        if(mHumidity == null)
        {
            mTextHumidity.text = sensor_error
        }
        if(mAcceleratie == null)
        {
            mTextAcceleratie.text = sensor_error
        }
        if(mCampMagnetic == null)
        {
            mTextCampMagnetic.text = sensor_error
        }
//        var sensorList = mSensorManager?.getSensorList(Sensor.TYPE_ALL)
//        var sensorText : StringBuilder = java.lang.StringBuilder()
//        if (sensorList != null) {
//            for(currentSensor:Sensor in sensorList)
//            {
//                sensorText.append(currentSensor.name).append(System.getProperty("line.separator"))
//            }
            //var sensorTextView: TextView = findViewById(R.id.sensor_list)
           // sensorTextView.setText(sensorText);
        //}
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        mDisplay = wm.defaultDisplay
    }

    @SuppressLint("StringFormatInvalid")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent) {
        SensorRegistrationTask().doInBackground(event)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onStart() {
        super.onStart()
        if(mSensorProximity!=null)
        {
            mSensorManager?.registerListener(this,mSensorProximity,SensorManager.SENSOR_DELAY_NORMAL)
        }
        if(mSensorLight!=null)
        {
            mSensorManager?.registerListener(this,mSensorLight,SensorManager.SENSOR_DELAY_NORMAL)
        }
        if(mPressure!=null)
        {
            mSensorManager?.registerListener(this,mPressure,SensorManager.SENSOR_DELAY_NORMAL)
        }
        if(mHumidity!=null)
        {
            mSensorManager?.registerListener(this,mHumidity,SensorManager.SENSOR_DELAY_NORMAL)
        }
        if(mAcceleratie!=null)
        {
            mSensorManager?.registerListener(this,mAcceleratie,SensorManager.SENSOR_DELAY_NORMAL)
        }
        if(mCampMagnetic!=null)
        {
            mSensorManager?.registerListener(this,mCampMagnetic,SensorManager.SENSOR_DELAY_NORMAL)
        }
        //SensorRegistrationTask().execute()
    }
    private inner class SensorRegistrationTask : AsyncTask<Void, Void, Void>() {
        @RequiresApi(Build.VERSION_CODES.O)
        fun doInBackground(event:SensorEvent): Void? {
            val sensorType: Int = event.sensor.type
            var currentValue : Float = event.values[0]
            when(sensorType)
            {
                Sensor.TYPE_LIGHT ->
                {
                    mTextSensorLight.text = getString(R.string.light_sensor_1_2f, currentValue)
                    window.decorView.setBackgroundColor(Color.argb(1f,255f,0f,currentValue))

                }
                Sensor.TYPE_PROXIMITY -> {
                    mTextSensorProximity.text = getString(R.string.proximity_sensor_1_2f, currentValue)
                    val img : ImageView = findViewById(R.id.imageView)
                    img.layoutParams.height = (currentValue * 40).toInt();
                    img.layoutParams.width = (currentValue * 40).toInt();
                    img.requestLayout()
                }
                Sensor.TYPE_PRESSURE -> {
                    mTextPressure.text = getString(R.string.pressure_1_2f, currentValue)
                }
                Sensor.TYPE_RELATIVE_HUMIDITY ->
                {
                    mTextHumidity.text = getString(R.string.relative_humidity,currentValue)
                }
                Sensor.TYPE_ACCELEROMETER ->
                {
                    mTextAcceleratie.text = getString(R.string.acceleratie,currentValue)
                    //if (event != null) {
                    mAccelerometerData = event.values.clone()
                    //}
                }
                Sensor.TYPE_MAGNETIC_FIELD ->
                {
                    mTextCampMagnetic.text = getString(R.string.camp_magnetic, currentValue)
                    //if (event != null) {
                    mMagnetometerData = event.values.clone()
                    //}

                }
                else -> {}

            }
            val rotationMatrix = FloatArray(9)
            val rotationOK = SensorManager.getRotationMatrix(
                rotationMatrix,
                null, mAccelerometerData, mMagnetometerData
            )
            var rotationMatrixAdjusted = FloatArray(9)
            when (mDisplay!!.rotation) {
                Surface.ROTATION_0 -> rotationMatrixAdjusted = rotationMatrix.clone()
                Surface.ROTATION_90 ->{
                    SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X,
                        rotationMatrixAdjusted);
                }
                Surface.ROTATION_180 -> {
                    SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_MINUS_X, SensorManager.AXIS_MINUS_Y,
                        rotationMatrixAdjusted);
                }
                Surface.ROTATION_270 -> {
                    SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X,
                        rotationMatrixAdjusted);
                }
            }

            val orientationValues = FloatArray(3)
            if (rotationOK) {
                SensorManager.getOrientation(rotationMatrixAdjusted, orientationValues);
            }
            val azimuth = orientationValues[0]
            var pitch = orientationValues[1]
            var roll = orientationValues[2]
            if (Math.abs(pitch) < 0.05f) {
                pitch = 0F;
            }
            if (Math.abs(roll) < 0.05f) {
                roll = 0F;
            }
            mTextAzimut.text = getString(R.string.azimut,azimuth)
            mTextRoll.text = getString(R.string.roll,roll)
            mTextPitch.text = getString(R.string.pitch,pitch)

            mSpotTop?.alpha = 0f
            mSpotBottom?.alpha = 0f
            mSpotLeft?.alpha = 0f
            mSpotRight?.alpha = 0f
            if (pitch > 0) {
                mSpotBottom?.alpha = pitch;
            } else {
                mSpotTop?.alpha = abs(pitch);
            }
            if (roll > 0) {
                mSpotLeft?.alpha = roll;
            } else {
                mSpotRight?.alpha = abs(roll);
            }
            return null
        }

        override fun doInBackground(vararg params: Void?): Void {
            TODO("Not yet implemented")
        }
    }
    override fun onStop()
    {
        super.onStop()
        mSensorManager?.unregisterListener(this)
    }
}