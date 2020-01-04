<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> {{ title }} </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="socio.username"
              label="Username"
              outlined
              dense
              disabled
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="socio.nome"
              label="Nome"
              :error-messages="nomeErrors"
              outlined
              dense
              @input="$v.socio.nome.$touch()"
              @blur="$v.socio.nome.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="socio.email"
              label="Email Address"
              :error-messages="emailErrors"
              outlined
              dense
              required
              @input="$v.socio.email.$touch()"
              @blur="$v.socio.email.$touch()"
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
  props: [ 'title', 'socio' ],
  mixins: [validationMixin],
  validations: {
    socio: {
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
      if (!this.$v.socio.nome.$dirty) { return errors }
      !this.$v.socio.nome.maxLength && errors.push('Nome deve ter no máximo 25 carateres.')
      !this.$v.socio.nome.required && errors.push('Name é obtigatório.')
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
      if (!this.$v.socio.email.$dirty) { return errors }
      !this.$v.socio.email.email && errors.push('Insira um email válido.')
      !this.$v.socio.email.required && errors.push('E-mail é obtigatório.')
      return errors
    }
  },
  methods: {
    close() {
      this.password = ''
      this.$emit("close");
    },
    edit () {
      this.$v.$touch()
      if (this.$v.$error) {
        return
      }
      if (this.password !== '') {
        //console.log(this.password)
        this.$axios.$put(`/api/socios/${this.socio.username}`, {
          password: this.password,
          nome: this.socio.nome,
          email: this.socio.email
        })
          .then(() => {
            this.password = ''
            this.close()
          })
      } else {
        this.$axios.$put(`/api/socios/${this.socio.username}`, {
          password: null,
          nome: this.socio.nome,
          email: this.socio.email
        })
          .then(() => {
            this.close()
          })
      }
    }
  }
}
</script>
