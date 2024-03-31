package com.zijin.core.design;


/**
 * 设计模式枚举
 */
public enum DesignModelEnum {

    SINGLETON("单例模式", null),
    SIMPLE_FACTORY("简单工厂", null),
    FACTORY_METHOD("工厂方法", null),
    ABSTRACT_FACTORY("抽象工厂", null),
    BUILDER("生成器", null),
    PROTOTYPE("原型模式", null),
    FACADE("外观模式", null),
    ADAPTER("适配器", null),
    BRIDGE("桥接模式", null),
    COMPOSITE("组合模式", null),
    DECORATOR("装饰器模式", null),
    FLYWEIGHT("享元模式", null),
    PROXY("代理模式", null),
    CHAIN_OF_RESPONSIBILITY("责任链模式", null),
    STRATEGY("策略模式", null),
    TEMPLATE_METHOD("模板方法", null),
    COMMAND("命令模式", null),
    OBSERVER("观察者", null),
    VISITOR("访问者",null),
    STATE("状态模式", null),
    INTERPRETER("解释器", null),
    ITERATOR("迭代器", null),
    MEDIATOR("中介者", null),
    MEMENTO("备忘录", null);

    private final String name;

    private final Class demo;

    DesignModelEnum(String name, Class demo) {
        this.name = name;
        this.demo = demo;
    }




}
