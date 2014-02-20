// Copyright (C) 2008 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.reviewdb.client;

public final class Change {
  /** Minimum database status constant for an open change. */
  private static final char MIN_OPEN = 'a';
  /** Database constant for {@link Status#NEW}. */
  public static final char STATUS_NEW = 'n';
  /** Database constant for {@link Status#SUBMITTED}. */
  public static final char STATUS_SUBMITTED = 's';
  /** Database constant for {@link Status#DRAFT}. */
  public static final char STATUS_DRAFT = 'd';
  /** Maximum database status constant for an open change. */
  private static final char MAX_OPEN = 'z';

  /** Database constant for {@link Status#MERGED}. */
  public static final char STATUS_MERGED = 'M';

  /** ID number of the first patch set in a change. */
  public static final int INITIAL_PATCH_SET_ID = 1;

  /**
   * Current state within the basic workflow of the change.
   *
   * <p>
   * Within the database, lower case codes ('a'..'z') indicate a change that is
   * still open, and that can be modified/refined further, while upper case
   * codes ('A'..'Z') indicate a change that is closed and cannot be further
   * modified.
   * */
  public static enum Status {
    /**
     * Change is open and pending review, or review is in progress.
     *
     * <p>
     * This is the default state assigned to a change when it is first created
     * in the database. A change stays in the NEW state throughout its review
     * cycle, until the change is submitted or abandoned.
     *
     * <p>
     * Changes in the NEW state can be moved to:
     * <ul>
     * <li>{@link #SUBMITTED} - when the Submit Patch Set action is used;
     * <li>{@link #ABANDONED} - when the Abandon action is used.
     * </ul>
     */
    NEW(STATUS_NEW),

    /**
     * Change is open, but has been submitted to the merge queue.
     *
     * <p>
     * A change enters the SUBMITTED state when an authorized user presses the
     * "submit" action through the web UI, requesting that Gerrit merge the
     * change's current patch set into the destination branch.
     *
     * <p>
     * Typically a change resides in the SUBMITTED for only a brief sub-second
     * period while the merge queue fires and the destination branch is updated.
     * However, if a dependency commit (a {@link PatchSetAncestor}, directly or
     * transitively) is not yet merged into the branch, the change will hang in
     * the SUBMITTED state indefinitely.
     *
     * <p>
     * Changes in the SUBMITTED state can be moved to:
     * <ul>
     * <li>{@link #NEW} - when a replacement patch set is supplied, OR when a
     * merge conflict is detected;
     * <li>{@link #MERGED} - when the change has been successfully merged into
     * the destination branch;
     * <li>{@link #ABANDONED} - when the Abandon action is used.
     * </ul>
     */
    SUBMITTED(STATUS_SUBMITTED),

    /**
     * Change is a draft change that only consists of draft patchsets.
     *
     * <p>
     * This is a change that is not meant to be submitted or reviewed yet. If
     * the uploader publishes the change, it becomes a NEW change.
     * Publishing is a one-way action, a change cannot return to DRAFT status.
     * Draft changes are only visible to the uploader and those explicitly
     * added as reviewers.
     *
     * <p>
     * Changes in the DRAFT state can be moved to:
     * <ul>
     * <li>{@link #NEW} - when the change is published, it becomes a new change;
     * </ul>
     */
    DRAFT(STATUS_DRAFT),

    /**
     * Change is closed, and submitted to its destination branch.
     *
     * <p>
     * Once a change has been merged, it cannot be further modified by adding a
     * replacement patch set. Draft comments however may be published,
     * supporting a post-submit review.
     */
    MERGED(STATUS_MERGED),

    /**
     * Change is closed, but was not submitted to its destination branch.
     *
     * <p>
     * Once a change has been abandoned, it cannot be further modified by adding
     * a replacement patch set, and it cannot be merged. Draft comments however
     * may be published, permitting reviewers to send constructive feedback.
     */
    ABANDONED('A');

    private final char code;
    private final boolean closed;

    private Status(final char c) {
      code = c;
      closed = !(MIN_OPEN <= c && c <= MAX_OPEN);
    }

    public char getCode() {
      return code;
    }

    public boolean isOpen() {
      return !closed;
    }

    public boolean isClosed() {
      return closed;
    }

    public static Status forCode(final char c) {
      for (final Status s : Status.values()) {
        if (s.code == c) {
          return s;
        }
      }
      return null;
    }
  }
}
