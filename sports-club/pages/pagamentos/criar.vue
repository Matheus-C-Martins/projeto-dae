<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline">{{ title }}</v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="pagamento.username"
              label="Username"
              :error-messages="usernameErrors"
              outlined
              dense
              @input="$v.pagamento.username.$touch()"
              @blur="$v.pagamento.username.$touch()"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="pagamento.tipoProduto"
              label="Tipo Produto"
              :error-messages="tipoProdutoErrors"
              outlined
              dense
              @input="$v.pagamento.tipoProduto.$touch()"
              @blur="$v.pagamento.tipoProduto.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="pagamento.quantidade"
              label="Quantidade"
              :error-messages="quantidadeErrors"
              prefix="€"
              outlined
              dense
              @input="$v.pagamento.quantidade.$touch()"
              @blur="$v.pagamento.quantidade.$touch()"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="pagamento.preco"
              label="Preço"
              :error-messages="precoErrors"
              prefix="€"
              outlined
              dense
              @input="$v.pagamento.preco.$touch()"
              @blur="$v.pagamento.preco.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
              <v-text-field
              v-model="pagamento.estado"
              label="Estado"
              :error-messages="estadoErrors"
              outlined
              dense
              @input="$v.pagamento.estado.$touch()"
              @blur="$v.pagamento.estado.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
    <v-card-actions style="padding-top: 0px">
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="close">Cancelar</v-btn>
      <v-btn color="blue darken-1" text @click="save">Criar</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable */
import { validationMixin } from 'vuelidate'
import { required, maxLength, minLength, minValue, helpers } from 'vuelidate/lib/validators'
export const valueRegex = helpers.regex('valueRegex', /^\d*\.?\d*$/i)

export default {
  props: ["title", "pagamento"],
  mixins: [validationMixin],
  validations: {
    pagamento: {
      username: { required, maxLength: maxLength(50) },
      tipoProduto: { required, maxLength: maxLength(50) },
      quantidade: { required, valueRegex, minValue: minValue(0.01) },
      preco: { required, valueRegex, minValue: minValue(0.01) },
      estado: { required, maxLength: maxLength(50) }
    }
  },
  computed: {
    usernameErrors () {
      const errors = []
      if (!this.$v.pagamento.username.$dirty) { return errors }
      !this.$v.pagamento.username.maxLength && errors.push('Username só pode ter 50 carateres, no máximo.')
      !this.$v.pagamento.username.required && errors.push('Username é necessário.')
      return errors
    },
    tipoProdutoErrors () {
      const errors = []
      if (!this.$v.pagamento.tipoProduto.$dirty) { return errors }
      !this.$v.pagamento.tipoProduto.maxLength && errors.push('Tipo Produto só pode ter 50 carateres, no máximo.')
      !this.$v.pagamento.tipoProduto.required && errors.push('Tipo Produto é necessária.')
      return errors
    },
    quantidadeErrors () {
      const errors = []
      if (!this.$v.pagamento.quantidade.$dirty) { return errors }
      !this.$v.pagamento.quantidade.valueRegex && errors.push('Quantidade tem de conter apenas números.')
      !this.$v.pagamento.quantidade.minValue && errors.push('Quantidade tem de ser maior que €0.01.')
      !this.$v.pagamento.quantidade.required && errors.push('Quantidade é necessário.')
      return errors
    },
    precoErrors () {
      const errors = []
      if (!this.$v.pagamento.preco.$dirty) { return errors }
      !this.$v.pagamento.preco.valueRegex && errors.push('Preço tem de conter apenas números.')
      !this.$v.pagamento.preco.minValue && errors.push('Preço tem de ser maior que €0.01.')
      !this.$v.pagamento.preco.required && errors.push('Preço é necessário.')
      return errors
    },
    estadoErrors () {
      const errors = []
      if (!this.$v.pagamento.estado.$dirty) { return errors }
      !this.$v.pagamento.estado.maxLength && errors.push('Estado só pode ter 50 carateres, no máximo.')
      !this.$v.pagamento.estado.required && errors.push('Estado Produto é necessária.')
      return errors
    }
  },
  methods: {
    close() {
      this.$emit("close");
    },
    save () {
      this.$v.$touch()
      if (this.$v.$error) {
        return
      }
      this.$axios.$post('/api/pagamentos', {
        username: this.pagamento.username,
        tipoProduto: this.pagamento.tipoProduto,
        quantidade: this.pagamento.quantidade,
        preco: this.pagamento.preco,
        estado: this.pagamento.estado
      })
      .then(() => {
        this.close()
      })
    }
  }
};
</script>