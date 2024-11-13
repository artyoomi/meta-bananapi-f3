DESCRIPTION = "BananaPi F3 Linux Kernel"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS = "u-boot-bananapi-f3 u-boot-mkimage-native dtc-native"

SRCREV ?= "ad8dc1c1ef3eb52a6b1bad64ee570e51b04fff10"
SRCREV_machine ?= "ad8dc1c1ef3eb52a6b1bad64ee570e51b04fff10"
SRCREV_machine:qemuriscv64 ?= "ad8dc1c1ef3eb52a6b1bad64ee570e51b04fff10"
SRCREV_machine:bananapi-f3 ?= "ad8dc1c1ef3eb52a6b1bad64ee570e51b04fff10"

KCONFIG_MODE = "--alldefconfig"

KERNEL_EXTRA_FEATURES ?= ""
KERNEL_FEATURES:append:qemuriscv64 = " cfg/virtio.scc"
KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"
KERNEL_FEATURES:remove = "features/debug/printk.scc"
KERNEL_FEATURES:remove = "features/kernel-sample/kernel-sample.scc"

require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://github.com/BPI-SINOVOIP/pi-linux.git;protocol=https;branch=linux-6.1.15-k1"

SRC_URI:append:bananapi-f3 = " file://defconfig"

SRC_URI[sha256sum] = "8a1ba920a7e805b847c222e5bc688da6172fbcf4eac5c6550db56ee72d16f8a0"


LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "6.1.15"
LINUX_VERSION_EXTENSION = ""

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"
KCONF_AUDIT_LEVEL = "2"
KCONF_BSP_AUDIT_LEVEL:qemuriscv64 = "1"
KCONF_AUDIT_LEVEL:qemuriscv64 = "1"
