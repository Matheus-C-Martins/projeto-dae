<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline">{{ title }}</v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model="administrador.username"
              label="Username"
              :error-messages="usernameErrors"
              outlined
              dense
              @input="$v.administrador.username.$touch()"
              @blur="$v.administrador.username.$touch()"
            ></v-text-field>
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
              v-model="administrador.password"
              :value="administrador.password"
              label="Password"
              :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="() => (value = !value)"
              :type="value ? 'password' : 'text'"
              :error-messages="passwordErrors"
              outlined
              dense
              @input="$v.administrador.password.$touch()"
              @blur="$v.administrador.password.$touch()"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
    <v-card-actions style="padding-top: 0px">
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="close"> Cancelar </v-btn>
      <v-btn color="blue darken-1" text @click="save"> Criar </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable */
import { validationMixin } from 'vuelidate'
import { required, maxLength, minLength, email } from 'vuelidate/lib/validators'

export default {
  props: ["title", "administrador"],
  mixins: [validationMixin],
  validations: {
    administrador: {
      nome: { required, maxLength: maxLength(25) },
      username: { required, maxLength: maxLength(10) },
      email: { required, email },
      password: { required, minLength: minLength(3) }
    }
  },
  data () {
    return {
      valid: true,
      value: true,
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
    usernameErrors () {
      const errors = []
      if (!this.$v.administrador.username.$dirty) { return errors }
      !this.$v.administrador.username.maxLength && errors.push('Username deve ter no máximo 10 carateres.')
      !this.$v.administrador.username.required && errors.push('Username é obtigatório.')
      return errors
    },
    passwordErrors () {
      const errors = []
      if (!this.$v.administrador.password.$dirty) { return errors }
      !this.$v.administrador.password.minLength && errors.push('Password deve ter no mínimo 3 carateres.')
      !this.$v.administrador.password.required && errors.push('Password é obtigatória.')
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
      this.$emit("close");
    },
    save () {
      this.$v.$touch()
      if (this.$v.$error) {
        return
      }
      this.$axios.$post('/api/administradores', {
        username: this.administrador.username,
        password: this.administrador.password,
        nome: this.administrador.nome,
        email: this.administrador.email
      })
      .then(() => {
        this.close()
      })
    }
  }
};
</script>
