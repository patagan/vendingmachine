<template>
  <v-dialog
    v-model="dialog"
    persistent
    width="1024"
    >
    <template v-slot:activator="{ props }">
        <v-list-item 
            v-show="isList"
            prepend-icon="mdi-view-dashboard" title="Add Product" v-bind="props">
        </v-list-item>
        <v-btn v-show="!isList" v-bind="props" icon="mdi-pencil"></v-btn>
    </template>
    <v-card>
        <v-card-title>
        <span class="text-h5">New Product</span>
        </v-card-title>
        <v-card-text>
        <v-form ref="productForm" v-model="firstValid">
            <v-row>
            <v-col
                cols="12"
                sm="6"
                md="4"
            >
                <v-text-field
                label="Product name*"
                v-model="product.name"
                required
                ></v-text-field>
            </v-col>
            <v-col cols="12">
                <v-text-field
                label="Cost*"
                v-model="product.cost"
                :rules="costRules"
                @change="validate"
                required
                ></v-text-field>
            </v-col>
            <v-col cols="12">
                <v-text-field
                label="Available amount*"
                v-model="product.amountAvailable"
                :rules="amountRules"
                @change="validate"
                required
                ></v-text-field>
            </v-col>
            </v-row>
        </v-form>
        <small>*indicates required field</small>
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
            @click="submitProduct"
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
  props: {
    isList:Boolean,
    existingProduct:Object
  },
  data() {
    return {
    product: {
        id:this.existingProduct ? this.existingProduct.id:"",
        name: this.existingProduct ? this.existingProduct.name:"",
        cost: this.existingProduct ? this.existingProduct.cost:"",
        amountAvailable: this.existingProduct ? this.existingProduct.amountAvailable:"",
        sellerId: this.loggedUser ? this.loggedUser.id : ""
    },
    costRules: [
        v => !!v || "Cost is required",
        () => /^\d+(\.[0-9][05]?)?$/.test(this.product.cost) || "The value should be numeric with maximun two decimals and multiple of 0.05. Example: 5.55"
    ],
    amountRules: [
    v => !!v || "Amount is required",
        () => /^\d+$/.test(this.product.amountAvailable) || "The value should be Integer"
    ],
    dialog: false,
    firstValid: true
    }
  },
  computed: {
      ...mapState({
          loggedUser: state => state.account.user
      })
    },
    methods: {
      ...mapActions(["addProduct", "updateProduct"]),
      submitProduct() {
        if (this.firstValid) {
          this.existingProduct ? this.updateProduct(this.product) : this.addProduct(this.product);
          this.dialog=false;
          this.product = {
            id: "",
            name:"",
            cost:"",
            amountAvailable:"",
            sellerId:""
          }
        }
      },
      validate() {
        this.$refs.productForm.validate();
      }
    }
}
</script>