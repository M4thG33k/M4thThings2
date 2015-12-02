package com.m4thg33k.m4ththings.proxies;

import com.m4thg33k.m4ththings.init.ModRenderers;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        super.registerRenderers();

        ModRenderers.init();
    }
}
