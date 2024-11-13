require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

DEPENDS += " dtc-native bc-native"

SUMMARY = "U-Boot bootloader for K1 board"
LICENSE = "GPL-2.0+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

SRC_URI = "git://github.com/BPI-SINOVOIP/pi-u-boot.git;protocol=https;branch=v2022.10-k1"
SRCREV = "2aa062b0567d3863d700021990de89ba6b616c8f"

SRC_URI += "file://board/spacemit/k1-x/config.mk.patch"

do_deploy:prepend() {
    ln -sf ${B}/u-boot.dtb ${B}/arch/riscv/dts/
    ln -sf ${B}/u-boot.dtb ${S}/
}

do_deploy:append() {
    install -m 0644 ${S}/u-boot.itb ${DEPLOY_DIR}/
    ln -sf ${B}/u-boot.dtb ${B}/arch/riscv/dts/
    ln -sf ${S}/FSBL.bin ${DEPLOY_DIR}/
}

do_install:append() {
    install -d ${D}/boot
    install -m 0644 ${UBOOT_BINARY} ${D}/boot/FSBL.bin
}

FILES:${PN} += "/boot/FSBL.bin"

COMPATIBLE_MACHINE = "(bananapi-f3|qemuriscv64)"

B = "${WORKDIR}/build"
