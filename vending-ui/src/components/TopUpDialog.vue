<template>
    <v-dialog
      v-model="dialog"
      persistent
      width="220"
      >
      <template v-slot:activator="{ props }">
          <v-list-item 
              prepend-icon="mdi-account-box" title="Top Up" v-bind="props">
          </v-list-item>
      </template>
      <v-card>
          <v-card-title>
          <span class="text-h5">Enter Coins</span>
          </v-card-title>
          <v-card-text>
            <v-row>
                <v-text-field label="5 Cents" suffix="coins" readonly variant="plain" v-model="coins.fiveCents">
                    <template v-slot:append>
                        <v-btn icon="mdi-plus" color="green" @click="coins.fiveCents++"></v-btn>
                    </template>
                    <template v-slot:prepend>
                        <v-btn icon="mdi-minus" color="red" @click="coins.fiveCents > 0 ? coins.fiveCents--:0"></v-btn>
                    </template>
                </v-text-field>
            </v-row>
            <v-row>
                <v-text-field label="10 Cents" suffix="coins" readonly variant="plain" v-model="coins.tenCents">
                    <template v-slot:append>
                        <v-btn icon="mdi-plus" color="green" @click="coins.tenCents++"></v-btn>
                    </template>
                    <template v-slot:prepend>
                        <v-btn icon="mdi-minus" color="red" @click="coins.tenCents > 0 ? coins.tenCents--:0"></v-btn>
                    </template>
                </v-text-field>
            </v-row>
            <v-row>
                <v-text-field label="20 Cents" suffix="coins" readonly variant="plain" v-model="coins.twentyCents">
                    <template v-slot:append>
                        <v-btn icon="mdi-plus" color="green" @click="coins.twentyCents++"></v-btn>
                    </template>
                    <template v-slot:prepend>
                        <v-btn icon="mdi-minus" color="red" @click="coins.twentyCents > 0 ? coins.twentyCents--:0"></v-btn>
                    </template>
                </v-text-field>
            </v-row>
            <v-row>
                <v-text-field label="50 Cents" suffix="coins" readonly variant="plain" v-model="coins.fiftyCents">
                    <template v-slot:append>
                        <v-btn icon="mdi-plus" color="green" @click="coins.fiftyCents++"></v-btn>
                    </template>
                    <template v-slot:prepend>
                        <v-btn icon="mdi-minus" color="red" @click="coins.fiftyCents > 0 ? coins.fiftyCents--:0"></v-btn>
                    </template>
                </v-text-field>
            </v-row>
            <v-row>
                <v-text-field label="100 Cents" suffix="coins" readonly variant="plain" v-model="coins.hundredCents">
                    <template v-slot:append>
                        <v-btn icon="mdi-plus" color="green" @click="coins.hundredCents++"></v-btn>
                    </template>
                    <template v-slot:prepend>
                        <v-btn icon="mdi-minus" color="red" @click="coins.hundredCents > 0 ? coins.hundredCents--:0"></v-btn>
                    </template>
                </v-text-field>
            </v-row>
          </v-card-text>
          <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="blue-darken-1"
              variant="text"
              @click="dialog = false"
          >
              Close
          </v-btn>
          <v-btn
              color="blue-darken-1"
              variant="text"
              @click="submitDeposit"
          >
              Save
          </v-btn>
          </v-card-actions>
      </v-card>
    </v-dialog>
  </template>

<script>
import { mapState, mapActions } from "vuex";
export default {
data() {
    return {
        coins: {
            fiveCents: 0,
            tenCents: 0,
            twentyCents: 0,
            fiftyCents: 0,
            hundredCents: 0
        },
        dialog:false
    }
},
methods: {
    ...mapActions(["topUp"]),
    submitDeposit() {
      this.topUp(this.coins);
      this.dialog = false
      this.coins = {
            fiveCents: 0,
            tenCents: 0,
            twentyCents: 0,
            fiftyCents: 0,
            hundredCents: 0
        };
    }
}
}
</script>