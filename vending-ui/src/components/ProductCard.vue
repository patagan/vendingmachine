<template>
    <v-card
      class="mx-auto"
      max-width="344"
    >
      <v-img
        src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
        height="100px"
        cover
      ></v-img>
  
      <v-card-title>
        {{product.name}}
      </v-card-title>
  
      <v-card-subtitle>
        {{ product.amountAvailable }} available
      </v-card-subtitle>

      <v-card-text class="py-0">
        {{ product.cost }} Euros
      </v-card-text>
  
      <v-card-actions>
        <BuyForm v-if="isBuyer" :product="product" :callback="buyAction"/>
        <v-spacer></v-spacer>
        <ProductForm v-if="isMyProduct" :isList="false" :existingProduct="product"/>
        <WarningDialog v-if="isMyProduct" iconBtn="mdi-delete" :warningText="createWarning" :callback="removeAction"/>
      </v-card-actions>

    </v-card>
  </template>

<script>
import { product } from '@/_store/product.module';
import ProductForm from '@/components/ProductForm.vue';
import WarningDialog from '@/components/WarningDialog.vue';
import BuyForm from '@/components/BuyForm.vue';
import { mapState, mapActions } from "vuex";
export default {
    props: {
        product:Object
    },
    components: { ProductForm, WarningDialog, BuyForm },
    computed: {
        ...mapState({
          loggedUser: state => state.account.user
        }),
        createWarning() {
            return "You choose to remove item: " + this.product.name
        },
        createBuyWarning() {
            return "Do you want to buy " + this.product.name
        },
        isMyProduct() {
          return this.loggedUser ? this.loggedUser.id === this.product.sellerId && this.loggedUser.role === "SELLER":false;
        },
        isBuyer() {
          return this.loggedUser ? this.loggedUser.role === "BUYER":false;
        }
    },
    methods: {
      ...mapActions(['deleteProduct', 'buyProduct','error']),
      removeAction() {
        this.deleteProduct(this.product.id);
      },
      buyAction(buyInput) {
        let amount = buyInput.amountPurchased;
        if(this.loggedUser.deposit >= (this.product.cost*amount) && this.product.amountAvailable >= amount) {
          this.buyProduct(buyInput)
        } else if(this.product.amountAvailable < amount) {
          this.error("No enough " + this.product.name + " left, please choose another item")
        } else {
          this.error("You do not have enought saldo, please top up your deposit")
        }
      }
    }
}
</script>