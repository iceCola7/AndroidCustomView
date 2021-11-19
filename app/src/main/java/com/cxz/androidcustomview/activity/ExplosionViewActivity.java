package com.cxz.androidcustomview.activity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.explosionlib.ExplosionField;
import com.cxz.explosionlib.factory.ExplodeParticleFactory;
import com.cxz.explosionlib.factory.FallingParticleFactory;

public class ExplosionViewActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_explosion_view;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        ExplosionField explosionField = new ExplosionField(this, new FallingParticleFactory());
        explosionField.addListener(findViewById(R.id.imageView));

        ExplosionField explosionField2 = new ExplosionField(this, new ExplodeParticleFactory());
        explosionField2.addListener(findViewById(R.id.imageView2));
    }
}