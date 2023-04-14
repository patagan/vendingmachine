<template>
  <v-app-bar
    color="teal-darken-4"
    image="https://picsum.photos/1920/1080?random"
  >
    <template v-slot:image>
      <v-img
        gradient="to top right, rgba(19,84,122,.8), rgba(128,208,199,.8)"
      ></v-img>
    </template>

    <v-app-bar-title>Vending Machine</v-app-bar-title>

    <v-spacer></v-spacer>

    <v-card-title>
      Saldo: {{ user ? user.deposit: 0 }} €
    </v-card-title>
  </v-app-bar>
</template>

<script>
  import { mapState } from "vuex";

    export default {
    computed: {
      ...mapState({
          user: state => state.account.user
      })
    }
}
</script>
