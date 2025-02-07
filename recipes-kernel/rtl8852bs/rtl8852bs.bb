SUMMARY = "Realtek RTL8188EU driver"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SECTION = "kernel/modules"

SRC_URI = "git://github.com/amazingfate/wifi-rtl8852bs.git;protocol=https;branch=main"

SRCREV = "56420ff22f9c174e23ef1d1fefc66cbed197bc12"

inherit module

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake KSRC=${STAGING_KERNEL_DIR}
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8852bs.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/
}

FILES_${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/8852bs.ko"
