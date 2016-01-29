<bean id="${daoID}" parent="parentDao">
    <property name="proxyInterfaces" value="${dao}"/>
    <property name="target">
        <bean parent="daoRealizeTarget">
            <constructor-arg value="${entitySimple}"/>
        </bean>
    </property>
</bean>
