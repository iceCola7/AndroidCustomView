package com.cxz.androidcustomview.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.explosionlib.ExplosionField;
import com.cxz.explosionlib.factory.ExplodeParticleFactory;
import com.cxz.explosionlib.factory.FallingParticleFactory;

public class ExplosionViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explosion_view);

        ExplosionField explosionField = new ExplosionField(this, new FallingParticleFactory());
        explosionField.addListener(findViewById(R.id.imageView));

        ExplosionField explosionField2 = new ExplosionField(this, new ExplodeParticleFactory());
        explosionField2.addListener(findViewById(R.id.imageView2));

    }
}