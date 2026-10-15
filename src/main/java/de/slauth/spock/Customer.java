package de.slauth.spock;

import java.time.LocalDate;

/// A customer of the shop.
///
/// @param memberSince the date the customer joined the loyalty program, or `null` if they are not a member
public record Customer(LocalDate memberSince) {
}
