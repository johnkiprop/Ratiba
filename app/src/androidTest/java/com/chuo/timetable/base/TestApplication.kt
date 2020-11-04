package com.chuo.timetable.base

class TestApplication: MyApplication(){
    override fun initComponent(): ApplicationComponent? {
        return DaggerTestApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}