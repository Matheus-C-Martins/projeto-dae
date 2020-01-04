<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> {{ title }} </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model='administrador.username'
              label='Username'
              outlined
              dense
              readonly
              disabled>
            </v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="administrador.nome"
              label="Nome"
              :error-messages="nomeErrors"
              outlined
              dense
              @input="$v.administrador.nome.$touch()"
              @blur="$v.administrador.nome.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="administrador.email"
              label="Email Address"
              :error-messages="emailErrors"
              outlined
              dense
              required
              @input="$v.administrador.email.$touch()"
              @blur="$v.administrador.email.$touch()"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="password"
              :value="password"
              label="Password"
              :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="() => (value = !value)"
              :type="value ? 'password' : 'text'"
              :error-messages="passwordErrors"
              outlined
              dense
              @input="$v.password.$touch()"
              @blur="$v.password.$touch()">
            </v-text-field>
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
import { required, maxLength, minLength, email } from 'vuelidate/lib/validators'

export default {
  props: [ 'title', 'administrador' ],
  mixins: [validationMixin],
  validations: {
    administrador: {
      nome: { required, maxLength: maxLength(25) },
      email: { required, email },
    },
    password: { minLength: minLength(3) }
  },
  data () {
    return {
      valid: true,
      value: true,
      password: ''
    }
  },
  computed: {
    nomeErrors () {
      const errors = []
      if (!this.$v.administrador.nome.$dirty) { return errors }
      !this.$v.administrador.nome.maxLength && errors.push('Nome deve ter no máximo 25 carateres.')
      !this.$v.administrador.nome.required && errors.push('Name é obtigatório.')
      return errors
    },
    passwordErrors () {
      const errors = []
      if (!this.$v.password.$dirty) { return errors }
      !this.$v.password.minLength && errors.push('Password deve ter no mínimo 3 carateres.')
      return errors
    },
    emailErrors () {
      const errors = []
      if (!this.$v.administrador.email.$dirty) { return errors }
      !this.$v.administrador.email.email && errors.push('Insira um email válido.')
      !this.$v.administrador.email.required && errors.push('E-mail é obtigatório.')
      return errors
    }
  },
  methods: {
    close() {
      this.password = ''
      this.$emit("close");
    },
    edit () {
      if (this.password !== '') {
        //console.log(this.password)
        this.$axios.$put(`/api/administradores/${this.administrador.username}`, {
          password: this.password,
          nome: this.administrador.nome,
          email: this.administrador.email
        })
          .then(() => {
            this.close()
          })
      } else {
        this.$axios.$put(`/api/administradores/${this.administrador.username}`, {
          password: null,
          nome: this.administrador.nome,
          email: this.administrador.email
        })
          .then(() => {
            this.password = ''
            this.close()
          })
      }
    }
  }
}
</script>
