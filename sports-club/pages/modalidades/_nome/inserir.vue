<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> Inserir Novo Escalão </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="escalao"
              label="Escalão"
              :error-messages="escalaoErrors"
              outlined
              dense
              @input="$v.escalao.$touch()"
              @blur="$v.escalao.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
    <v-card-actions style="padding-top: 0px">
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="close"> Cancelar </v-btn>
      <v-btn color="blue darken-1" text @click="save"> Inserir </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable */
import { validationMixin } from 'vuelidate'
import { required, maxLength, minLength, helpers } from 'vuelidate/lib/validators'
export const escalaoRegex = helpers.regex('valueRegex', /^(Sub-[0-2]\d)$/i)

export default {
  props: [ 'nome' ],
  mixins: [validationMixin],
  validations: {
    escalao: { required, maxLength: maxLength(6), minLength: minLength(6), escalaoRegex }
  },
  data() {
    return {
      escalao: ''
    };
  },
  computed: {
    escalaoErrors () {
      const errors = []
      if (!this.$v.escalao.$dirty) { return errors }
      !this.$v.escalao.maxLength && errors.push('Escalão deve ser escrito no formato: "Sub-XX".')
      !this.$v.escalao.minLength && errors.push('Escalão deve ser escrito no formato: "Sub-XX".')
      !this.$v.escalao.escalaoRegex && errors.push('Escalão deve ser escrito no formato: "Sub-XX".')
      !this.$v.escalao.required && errors.push('Escalão é obrigatório.')
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
      this.$axios.$post('/api/modalidades', {
        nome: this.nome,
        escalao: this.escalao
      })
      .then(() => {
        this.close()
      })
    }
  }
};
</script>
