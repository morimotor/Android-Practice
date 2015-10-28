package jp.morimotor.androidpractice;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class AccelerationActivity extends AppCompatActivity{
    private final String TAG = AccelerationActivity.class.getSimpleName();
    AccelerometerAdapter accelerometerAdapter;
    GraphThread thread;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceleration);

        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerAdapter = new AccelerometerAdapter(manager);
        thread = new GraphThread(accelerometerAdapter);
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accelerometerAdapter.stopSensor();
        thread.close();
    }


    class GraphThread extends Thread {
        AccelerometerAdapter adapter;
        Handler handler = new Handler();

        boolean runflg = true;

        public GraphThread(AccelerometerAdapter adapter) {
            this.adapter = adapter;

        }

        public void close() {
            runflg = false;
        }

        public void run() {
            while (runflg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.t_x)).setText("x axis:" + adapter.getDx());
                        ((TextView)findViewById(R.id.t_y)).setText("y axis:" + adapter.getDy());
                        ((TextView)findViewById(R.id.t_z)).setText("z axis:" + adapter.getDz());
                        ((GraphView)findViewById(R.id.graphViewX)).setDiv(adapter.getDx());
                        ((GraphView)findViewById(R.id.graphViewY)).setDiv(adapter.getDy());
                        ((GraphView)findViewById(R.id.graphViewZ)).setDiv(adapter.getDz());
                        findViewById(R.id.graphViewX).invalidate();
                        findViewById(R.id.graphViewY).invalidate();
                        findViewById(R.id.graphViewZ).invalidate();
                    }
                });
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class AccelerometerAdapter implements SensorEventListener {

        private SensorManager manager;

        private float oldx = 0;
        private float oldy = 0;
        private float oldz = 0;

        private float dx = 0;
        private float dy = 0;
        private float dz = 0;

        public float getDx() {
            return dx;
        }

        public void setDx(float dx) {
            this.dx = dx;
        }

        public float getDy() {
            return dy;
        }

        public void setDy(float dy) {
            this.dy = dy;
        }

        public float getDz() {
            return dz;
        }

        public void setDz(float dz) {
            this.dz = dz;
        }

        public AccelerometerAdapter(SensorManager manager) {
            List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);

            if (sensors.size() > 0) {
                Sensor s = sensors.get(0);
                manager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
            }
        }

        public void stopSensor() {
            if ( manager != null )
                manager.unregisterListener(this);
            manager = null;
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                dx = event.values[0] - oldx;
                dy = event.values[1] - oldy;
                dz = event.values[2] - oldz;
                oldx = event.values[0];
                oldy = event.values[1];
                oldz = event.values[2];
            }
        }

    }


}
