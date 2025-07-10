#
# Java Makefile
#
# 2004.02.17
#
# Package : org.kipo.wlmonitor

PKG_CLS=$(J_CLS)/org/kipo/wlmonitor

CLASSES = $(PKG_CLS)/Sample.class \
	$(PKG_CLS)/Config.class \
	$(PKG_CLS)/MBeanMethod.class \
	$(PKG_CLS)/Display.class \
	$(PKG_CLS)/WLMonitor.class

all	:$(CLASSES)
$(PKG_CLS)/Sample.class	: Sample.java
	echo "========$@ Compile ========
	$(JAVAC) -d $(J_CLS) -classpath $(CLASSPATH) $(?F:.class=.java)
	echo	

$(PKG_CLS)/MBeanMethod.class	: MBeanMethod.java
	echo "========$@ Compile ========
	$(JAVAC) -d $(J_CLS) -classpath $(CLASSPATH) $(?F:.class=.java)
	echo	

$(PKG_CLS)/Config.class	: Config.java
	echo "========$@ Compile ========
	$(JAVAC) -d $(J_CLS) -classpath $(CLASSPATH) $(?F:.class=.java)
	echo
	
$(PKG_CLS)/Display.class	: Display.java
	echo "========$@ Compile ========
	$(JAVAC) -d $(J_CLS) -classpath $(CLASSPATH) $(?F:.class=.java)
	echo	

$(PKG_CLS)/WLMonitor.class	: WLMonitor.java
	echo "========$@ Compile ========
	$(JAVAC) -d $(J_CLS) -classpath $(CLASSPATH) $(?F:.class=.java)
	echo
