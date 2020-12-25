<template>
    <q-dialog v-model="show" persistent>
        <q-card style="width : 551px;">
            <q-card-section class="q-gutter-y-md">
                <img  id="thumbnailImg" :src="filePath">
                <q-file label="프로필 이미지" v-model="file" accept=".jpg, image/*" @input="imageChange" />
                <div class="flex justify-end q-gutter-x-sm">
                    <q-btn label="확인" />
                    <q-btn label="취소" @click="onCancel" />
                </div>
            </q-card-section>
        </q-card>
    </q-dialog>
</template>

<script>
export default {
    props : [ 'show' ],
    data () {
        return {
            file : null,
            filePath : ''
        }
    },
    methods : {
        imageChange: function(e){
            
        },

        onSubmit : function() {
            this.$emit('submit')
        },
        onCancel : function () {
            this.$emit('cancel')
        }
    }
}
</script>