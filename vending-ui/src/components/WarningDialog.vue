<template>
    <v-dialog
    v-model="dialog"
    persistent
    width="auto"
    >
    <template v-slot:activator="{ props }">
        <v-btn :icon="iconBtn"  v-bind="props"></v-btn>
    </template>
    <v-card>
        <v-card-title class="text-h5">
        Are you sure ou want to proceed?
        </v-card-title>
        <v-card-text>{{ warningText }}</v-card-text>
        <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
            color="green-darken-1"
            variant="text"
            @click="dialog = false"
        >
            Disagree
        </v-btn>
        <v-btn
            color="green-darken-1"
            variant="text"
            @click="submitAction"
        >
            Agree
        </v-btn>
        </v-card-actions>
    </v-card>
    </v-dialog>
  </template>

<script>
    import { mapState, mapActions } from "vuex";
    export default {
    props: {
        iconBtn:String,
        warningText:String,
        callback:Function
    },
    data() {
        return {
        dialog: false,
        }
    },
    methods: {
        ...mapActions(["addProduct", "updateProduct"]),
        submitAction() {
        this.callback();
        this.dialog = false
        }
    }
}
</script>