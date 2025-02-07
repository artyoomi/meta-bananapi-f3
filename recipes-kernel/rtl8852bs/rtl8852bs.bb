SUMMARY = "Realtek RTL8188EU driver"
# LICENSE = "GPLv2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SECTION = "kernel/modules"

SRC_URI = "git://github.com/amazingfate/wifi-rtl8852bs.git;protocol=https;branch=main"
# SRC_URI[sha256sum] = "8905311a346de3faebfa0af25a106839954ef1b1ccd3e028963d5f0872ced15b"

# SRC_URI = "file://Makefile"

SRCREV = "56420ff22f9c174e23ef1d1fefc66cbed197bc12"

inherit module

S = "${WORKDIR}/git"

# EXTRA_OEMAKE += "KSRC=${STAGING_KERNEL_DIR}"

do_compile() {
    oe_runmake KSRC=${STAGING_KERNEL_DIR}
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
    install -m 0644 ${S}/8852bs.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/
}

FILES_${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/8852bs.ko"
