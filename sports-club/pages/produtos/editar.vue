<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> {{ title }} </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model='produto.tipo'
              label='Tipo'
              outlined
              dense
              disabled>
            </v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="produto.valorBase"
              label="Valor Base"
              :error-messages="valorBaseErrors"
              prefix="€"
              outlined
              dense
              @input="$v.produto.valorBase.$touch()"
              @blur="$v.produto.valorBase.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="produto.descrição"
              label="Descrição"
              :error-messages="descriçãoErrors"
              outlined
              dense
              @input="$v.produto.descrição.$touch()"
              @blur="$v.produto.descrição.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text> 
    <v-card-actions style="padding-top: 0px">
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="close"> Cancelar </v-btn>
        <v-btn color="blue darken-1" text @click="edit"> Editar </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable */
import { validationMixin } from 'vuelidate'
import { required, maxLength, minLength, minValue, helpers } from 'vuelidate/lib/validators'
export const valueRegex = helpers.regex('valueRegex', /^\d*\.?\d*$/i)

export default {
  props: [ 'title', 'produto' ],
  mixins: [validationMixin],
  validations: {
    produto: {
      descrição: { required, maxLength: maxLength(255) },
      valorBase: { required, valueRegex, minValue: minValue(0.01) }
    }
  },
  computed: {
    descriçãoErrors () {
      const errors = []
      if (!this.$v.produto.descrição.$dirty) { return errors }
      !this.$v.produto.descrição.maxLength && errors.push('Descrição só pode ter 255 carateres, no máximo.')
      !this.$v.produto.descrição.required && errors.push('Descrição é necessária.')
      return errors
    },
    valorBaseErrors () {
      const errors = []
      if (!this.$v.produto.valorBase.$dirty) { return errors }
      !this.$v.produto.valorBase.valueRegex && errors.push('Valor Base tem de conter apenas números.')
      !this.$v.produto.valorBase.minValue && errors.push('Valor Base tem de ser maior que €0.01.')
      !this.$v.produto.valorBase.required && errors.push('Valor Base é necessário.')
      return errors
    }
  },
  methods: {
    close() {
      this.$emit("close");
    },
    edit () {
      this.$axios.$put(`/api/produtos/${this.produto.tipo}`, {
        descrição: this.produto.descrição,
        valorBase: this.produto.valorBase
      })
      .then(() => {
        this.close()
      })
    }
  }
}
</script>
