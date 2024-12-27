# **Online Voting System**

A secure and scalable online voting platform built using **Spring Boot** for the backend and **Next.js** for the frontend. The system integrates role-based authentication, OTP-based login via Twilio, and batch vote processing for optimized performance.

---

## **Project Setup**

Follow these steps to set up and run the project.

### **Prerequisites**
Ensure the following are installed on your system:
- **Java** (17+)
- **Maven**
- **Node.js** (16+)
- **npm** (or **yarn**, if preferred)

---

### **Backend (Spring Boot)**

1. **Navigate to your Spring Boot project folder**:  
   ```bash
   cd voting-system-server
   ```

2. **Add Twilio credentials**:  
   Open the `application.properties` file and add your Twilio credentials:  
   ```properties
   twilio.account.sid=your_twilio_account_sid
   twilio.auth.token=your_twilio_auth_token
   twilio.phone.number=your_twilio_phone_number
   ```

3. **Run the server**:  
   Use Maven to start the backend server:  
   ```bash
   mvn spring-boot:run
   ```

4. **Access the backend**:  
   By default, the server listens at:  
   - `http://localhost:8080`

---

### **Frontend (Next.js)**

1. **Navigate to the frontend folder**:  
   ```bash
   cd frontend
   ```

2. **Install dependencies**:  
   Use npm (or yarn) to install the required packages:  
   ```bash
   npm install
   ```

3. **Add environment variables**:  
   Create a `.env` file in the frontend folder with the following content:  
   ```env
   NEXT_PUBLIC_API_URL=http://localhost:8080
   ```

4. **Start the development server**:  
   Run the following command:  
   ```bash
   npm run dev
   ```

5. **Access the frontend**:  
   The application will be available at:  
   - `http://localhost:3000`

---