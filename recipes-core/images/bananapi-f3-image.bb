inherit core-image

DEPENDS += " opensbi-k1 genimage-native dosfstools-native mtools-native"

IMAGE_FSTYPES += "ext4"

IMAGE_INSTALL += " \
    packagegroup-core-boot \
    packagegroup-core-ssh-openssh \
    ${CORE_IMAGE_EXTRA_INSTALL} \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "file://env_k1-x.txt \
           file://u-boot-bpi-f3-512b.img \
           file://u-boot-bpi-f3-header-sd-0k.img \
           file://regulatory.db \
           file://regulatory.db.p7s \
           file://esos.elf"

do_image_preprocess() {
    cp ${THISDIR}/files/env_k1-x.txt ${DEPLOY_DIR_IMAGE}/

    install -D -m 0644 ${THISDIR}/files/u-boot-bpi-f3-512b.img ${DEPLOY_DIR_IMAGE}/
    install -D -m 0644 ${THISDIR}/files/u-boot-bpi-f3-header-sd-0k.img ${DEPLOY_DIR_IMAGE}/
    install -d ${IMAGE_ROOTFS}/lib/firmware/
    install -D -m 0744 ${THISDIR}/files/esos.elf ${IMAGE_ROOTFS}/lib/firmware/
    install -D -m 0644 ${THISDIR}/files/regulatory.db ${IMAGE_ROOTFS}/lib/firmware/
    install -D -m 0644 ${THISDIR}/files/regulatory.db.p7s ${IMAGE_ROOTFS}/lib/firmware/

    # Kernel image conversion
    if [ -f ${DEPLOY_DIR_IMAGE}/Image ]; then
        ${WORKDIR}/mkimage -n 'Kernel Image' -A riscv -O linux -C none -T kernel -a 0x01400000 -e 0x01400000 -d ${DEPLOY_DIR_IMAGE}/Image ${WORKDIR}/Image.itb
    fi

    # DTB copy
    if [ -f ${DEPLOY_DIR_IMAGE}/k1-bananapi-f3.dtb ]; then
        cp ${DEPLOY_DIR_IMAGE}/k1-bananapi-f3.dtb ${DEPLOY_DIR_IMAGE}/k1-x_deb1.dtb
    fi

    # OpenSBI copy
    cp ${DEPLOY_DIR}/fw_dynamic.itb ${DEPLOY_DIR_IMAGE}/
}

do_image_preprocess[depends] += "opensbi:do_deploy"

IMAGE_PREPROCESS_COMMAND += "do_image_preprocess;"

GENIMAGE_CONFIG = "${THISDIR}/conf/genimage/bananapi-f3-image.cfg"

# This task uses genimage to create the final image
# TODO: Replace with wic
do_image_gen() {
    GENIMAGE_TMP="${WORKDIR}/genimage.tmp"

    trap 'rm -rf "${ROOTPATH_TMP}"' EXIT
    ROOTPATH_TMP="$(mktemp -d)"

    rm -rf "${GENIMAGE_TMP}/*"

    ln -sf ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.cpio.gz ${DEPLOY_DIR_IMAGE}/rootfs.cpio.gz
    ln -sf ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.ext4 ${DEPLOY_DIR_IMAGE}/rootfs.ext4
    ln -sf ${DEPLOY_DIR}/FSBL.bin ${DEPLOY_DIR_IMAGE}/
    ln -sf ${DEPLOY_DIR}/u-boot.itb ${DEPLOY_DIR_IMAGE}/

    genimage \
             --rootpath=${ROOTPATH_TMP} \
             --tmppath=${GENIMAGE_TMP} \
             --inputpath=${DEPLOY_DIR_IMAGE} \
             --outputpath=${DEPLOY_DIR_IMAGE} \
             --config=${GENIMAGE_CONFIG}
}

addtask image_gen after do_image_complete before do_populate_lic_deploy
