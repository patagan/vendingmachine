<template>
    <v-dialog
      v-model="dialog"
      persistent
      width="520"
      >
        <template v-slot:activator="{ props }">
            <v-btn icon="mdi-shopping"  v-bind="props"></v-btn>
        </template>
        <v-card>
            <v-card-title>
            <span class="text-h5">How many {{ product.name }} do you want to buy?</span>
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-text-field :suffix="product.name" readonly variant="plain" v-model="buyInput.amountPurchased">
                        <template v-slot:append>
                            <v-btn icon="mdi-plus" color="green" @click="buyInput.amountPurchased++"></v-btn>
                        </template>
                        <template v-slot:prepend>
                            <v-btn icon="mdi-minus" color="red" @click="buyInput.amountPurchased > 0 ? buyInput.amountPurchased--:0"></v-btn>
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
                @click="buyAction"
            >
                Save
            </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
  </template>

<script>
export default {
    props: {
        product: Object,
        callback:Function
    },
    data() {
        return {
            buyInput: {
                productId:this.product.id,
                amountPurchased:1
            },
            dialog:false
        }
    },
    methods: {
        buyAction() {
            this.callback(this.buyInput);
            this.dialog = false
            this.buyInput.amountPurchased = 1
        }
    }
}
</script>