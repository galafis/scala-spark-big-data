package com.galafis.bigdata.models

import java.sql.Timestamp

/**
 * Data models for the application
 */
object DataModels {
  
  /**
   * Represents a transaction record
   * 
   * @param id Unique identifier
   * @param timestamp Transaction timestamp
   * @param customerId Customer identifier
   * @param amount Transaction amount
   * @param category Transaction category
   * @param merchantId Merchant identifier
   * @param location Transaction location
   * @param deviceType Device used for transaction
   * @param isFraud Whether the transaction is fraudulent
   */
  case class Transaction(
    id: String,
    timestamp: Timestamp,
    customerId: String,
    amount: Double,
    category: String,
    merchantId: String,
    location: String,
    deviceType: String,
    isFraud: Boolean
  )
  
  /**
   * Represents a customer record
   * 
   * @param id Customer identifier
   * @param name Customer name
   * @param email Customer email
   * @param registrationDate Registration date
   * @param segment Customer segment
   * @param age Customer age
   * @param gender Customer gender
   * @param location Customer location
   */
  case class Customer(
    id: String,
    name: String,
    email: String,
    registrationDate: Timestamp,
    segment: String,
    age: Int,
    gender: String,
    location: String
  )
  
  /**
   * Represents a merchant record
   * 
   * @param id Merchant identifier
   * @param name Merchant name
   * @param category Merchant category
   * @param location Merchant location
   * @param rating Merchant rating
   */
  case class Merchant(
    id: String,
    name: String,
    category: String,
    location: String,
    rating: Double
  )
  
  /**
   * Represents an aggregated transaction summary
   * 
   * @param customerId Customer identifier
   * @param totalAmount Total transaction amount
   * @param transactionCount Number of transactions
   * @param avgAmount Average transaction amount
   * @param maxAmount Maximum transaction amount
   * @param minAmount Minimum transaction amount
   * @param topCategory Most frequent category
   * @param fraudCount Number of fraudulent transactions
   */
  case class TransactionSummary(
    customerId: String,
    totalAmount: Double,
    transactionCount: Long,
    avgAmount: Double,
    maxAmount: Double,
    minAmount: Double,
    topCategory: String,
    fraudCount: Long
  )
  
  /**
   * Represents a fraud detection result
   * 
   * @param transactionId Transaction identifier
   * @param customerId Customer identifier
   * @param timestamp Transaction timestamp
   * @param amount Transaction amount
   * @param fraudProbability Probability of fraud
   * @param isFraud Whether the transaction is classified as fraudulent
   * @param riskScore Risk score
   * @param riskFactors Risk factors
   */
  case class FraudDetectionResult(
    transactionId: String,
    customerId: String,
    timestamp: Timestamp,
    amount: Double,
    fraudProbability: Double,
    isFraud: Boolean,
    riskScore: Double,
    riskFactors: Seq[String]
  )
}

